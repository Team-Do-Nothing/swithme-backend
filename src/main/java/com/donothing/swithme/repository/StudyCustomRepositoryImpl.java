package com.donothing.swithme.repository;

import static com.donothing.swithme.domain.QBookmark.bookmark;
import static com.donothing.swithme.domain.QStudy.study;

import com.donothing.swithme.domain.Bookmark;
import com.donothing.swithme.domain.QBookmark;
import com.donothing.swithme.domain.QStudy;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.study.StudyDetailResponseDto;
import com.donothing.swithme.dto.study.StudySearchRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

@Component
public class StudyCustomRepositoryImpl implements StudyCustomRepository {
    private final JPAQueryFactory queryFactory;

    public StudyCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<StudyDetailResponseDto> searchStudies(StudySearchRequest request, Pageable pageable) {
        QStudy qStudy = new QStudy("study");
        QBookmark qBookmark = new QBookmark("bookmark");

        List<Study> studyList =
                queryFactory.select(
                        Projections.fields(Study.class,
                        qStudy.studyId,
                        qStudy.title,
                        qStudy.studyInfo,
                        qStudy.studyStatus,
                        qStudy.studyType,
                        qStudy.numberOfMembers,
                        qStudy.remainingNumber,
                        qStudy.dateStudyStart,
                        qStudy.dateStudyEnd,
                        qStudy.member.memberId,
                        qStudy.member.as("member")))  // study.member 추가
                .from(study)
                .where(titleEq(request.getTitle()))
                .offset(pageable.getOffset()) // 스킵하고 몇번째부터 시작할건지
                .limit(pageable.getPageSize()) // 한번 조회할 때 몇개까지
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(study.count())
                .from(study);

        List<StudyDetailResponseDto> result;

        if (request.getMemberId() == null) {
            result = studyList.stream().map(StudyDetailResponseDto::new).collect(Collectors.toList());
        } else {
            List<Bookmark> bookmarkList = queryFactory.select(
                            Projections.fields(Bookmark.class,
                                    qBookmark.study.as("study")))
                    .from(bookmark)
                    .where(memberIdEq(request.getMemberId()))
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            Map<Long, Boolean> bookmarkMap = bookmarkList.stream()
                    .collect(Collectors.toMap(
                            b -> b.getStudy().getStudyId(),
                            b -> true,
                            (existing, replacement) -> existing
                    ));

            result = studyList.stream().map(study -> {
                boolean isBookmarked = bookmarkMap.getOrDefault(study.getStudyId(), false);
                return new StudyDetailResponseDto(study, isBookmarked);
            }).collect(Collectors.toList());
        }

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }

    private BooleanExpression titleEq(String title) {
        return title != null ? study.title.eq(title) : null;
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? bookmark.member.memberId.eq(memberId) : null;
    }

}
