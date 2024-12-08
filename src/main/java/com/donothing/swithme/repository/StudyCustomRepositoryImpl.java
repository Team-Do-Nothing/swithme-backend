package com.donothing.swithme.repository;

import static com.donothing.swithme.domain.QBookmark.bookmark;
import static com.donothing.swithme.domain.QStudy.study;
import static com.donothing.swithme.domain.QComment.comment1;

import com.donothing.swithme.common.SortOrder;
import com.donothing.swithme.domain.Bookmark;
import com.donothing.swithme.domain.Comment;
import com.donothing.swithme.domain.QBookmark;
import com.donothing.swithme.domain.QComment;
import com.donothing.swithme.domain.QStudy;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.study.StudyDetailResponseDto;
import com.donothing.swithme.dto.study.StudySearchRequest;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
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
        QComment qComment = new QComment("comment");
        OrderSpecifier<?> orderBy = request.getOrder() == SortOrder.DESC?
                qStudy.studyId.desc() : qStudy.studyId.asc();

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
                .orderBy(orderBy)
                .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(study.count())
                .where(titleEq(request.getTitle()))
                .from(study);

        List<Comment> commentList =
                queryFactory.select(
                                Projections.fields(Comment.class,
                                        qComment.commentId,
                                        qComment.study.as("study")))
                        .from(comment1)
                        .fetch();

        List<StudyDetailResponseDto> result;
        // commentList를 사용하여 각 Study의 studyId별 comment 갯수를 count하는 commentMap 생성
        Map<Long, Long> commentMap = commentList.stream()
                .collect(Collectors.groupingBy(comment -> comment.getStudy().getStudyId(), Collectors.counting()));

        if (request.getMemberId() == null) {
            // studyList에서 각 study에 맞는 commentCount를 포함하여 StudyDetailResponseDto 생성
            result = studyList.stream()
                    .map(study -> {
                        long commentCount = commentMap.getOrDefault(study.getStudyId(), 0L); // studyId에 맞는 comment 갯수
                        return new StudyDetailResponseDto(study, commentCount);
                    })
                    .collect(Collectors.toList());
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
                long commentCount = commentMap.getOrDefault(study.getStudyId(), 0L); // studyId에 맞는 comment 갯수
                return new StudyDetailResponseDto(study, isBookmarked, commentCount);
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
