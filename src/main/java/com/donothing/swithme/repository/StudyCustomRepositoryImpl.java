package com.donothing.swithme.repository;

import static com.donothing.swithme.domain.QStudy.study;

import com.donothing.swithme.domain.QMember;
import com.donothing.swithme.domain.QStudy;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.study.StudyDetailResponseDto;
import com.donothing.swithme.dto.study.StudyListResponseDto;
import com.donothing.swithme.dto.study.StudySearchRequest;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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
    public Page<Study> searchStudies(StudySearchRequest request, Pageable pageable) {
        QStudy qStudy = new QStudy("study");
        QMember qMember = new QMember("member");

        List<Study> studyList = queryFactory.select(Projections.fields(Study.class,
                        qStudy.studyId,
                        qStudy.title,
                        qStudy.studyInfo,
                        qStudy.studyStatus,
                        qStudy.studyType,
                        qStudy.numberOfMembers,
                        qStudy.dateStudyStart,
                        qStudy.dateStudyEnd,
                        qStudy.member.memberId,
                        qStudy.member.as("member")))  // study.member 추가
                .from(study)
                .where(titleEq(request.getTitle()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

//        List<StudyDetailResponseDto> result = studyList.stream().map(StudyDetailResponseDto::new).collect(Collectors.toList());
        JPAQuery<Long> countQuery = queryFactory.select(study.count())
                .from(study);

        return PageableExecutionUtils.getPage(studyList, pageable, countQuery::fetchOne);
    }

    private BooleanExpression titleEq(String title) {
        return title != null ? study.title.eq(title) : null;
    }
}
