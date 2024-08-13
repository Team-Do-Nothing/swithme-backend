package com.donothing.swithme.repository;

import com.donothing.swithme.domain.Comment;
import com.donothing.swithme.domain.QComment;
import com.donothing.swithme.dto.study.StudyCommentListResponseDto;

import static com.donothing.swithme.domain.QComment.comment1;
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
public class CommentCustomCustomRepositoryImpl implements CommentCustomRepository {

    private final JPAQueryFactory queryFactory;

    public CommentCustomCustomRepositoryImpl(EntityManager em) { this.queryFactory = new JPAQueryFactory(em); };

    @Override
    public Page<StudyCommentListResponseDto> findByComment(Long studyId, Pageable pageable) {
        QComment qComment = new QComment("comment");

        List<Comment> allComments =
                queryFactory.select(
                        Projections.fields(Comment.class,
                                qComment.commentId,
                                qComment.comment,
                                qComment.commentTag,
                                qComment.member.as("member")
                        )
                ).from(comment1)
                        .where(studyEq(studyId))
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        JPAQuery<Long> countQuery = queryFactory.select(comment1.count())
                .from(comment1);

        // 대댓글이 아닌 댓글들을 추출하여 StudyCommentListResponseDto로 매핑
        List<StudyCommentListResponseDto> comments = allComments.stream()
                .filter(c -> c.getCommentTag() == null) // 대댓글이 아닌 댓글 필터링
                .map(StudyCommentListResponseDto::new)
                .collect(Collectors.toList());

        // 각 댓글에 대해 대댓글 추가
        for (StudyCommentListResponseDto comment : comments) {
            List<StudyCommentListResponseDto> recomment = allComments.stream()
                    .filter(c -> c.getCommentTag() != null && c.getCommentTag().equals(comment.getCommentId()))
                    .map(StudyCommentListResponseDto::new)
                    .collect(Collectors.toList());

            comment.setRecomment(recomment);
        }

        return PageableExecutionUtils.getPage(comments, pageable, countQuery::fetchOne);
    }

    private BooleanExpression studyEq(Long studyId) {
        return studyId != null ? comment1.study.studyId.eq(studyId) : null;
    }
}
