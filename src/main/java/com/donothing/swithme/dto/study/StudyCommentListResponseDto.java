package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.Comment;
import com.donothing.swithme.dto.member.MemberInfoResponseDto;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class StudyCommentListResponseDto {
    private long commentId;
    private MemberInfoResponseDto memberInfo;
    private String comment;
    private LocalDateTime dateCreated;
    private boolean isDeleted;
    private List<StudyCommentListResponseDto> recommnet;

    public StudyCommentListResponseDto(Comment comment, List<Comment> allComments) {
        List<StudyCommentListResponseDto> recommnet = new ArrayList<>(); // 대댓글을 담을 리스트
        this.commentId = comment.getCommentId();
        this.memberInfo = MemberInfoResponseDto.builder().
                memberId(comment.getMember().getMemberId()).
                name(comment.getMember().getName()).
                nickname(comment.getMember().getNickname()).
                build();
        this.comment = comment.getComment();
        this.dateCreated = comment.getDateCreated();
        this.commentId = comment.getCommentId();
        this.commentId = comment.getCommentId();

        for (Comment c : allComments) {
            if (c.getCommentTag().equals(comment.getCommentId())) {
                recommnet.add(new StudyCommentListResponseDto(c));
            }
        }

        this.recommnet = recommnet;
    }

    public StudyCommentListResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.memberInfo = MemberInfoResponseDto.builder().
                memberId(comment.getMember().getMemberId()).
                name(comment.getMember().getName()).
                nickname(comment.getMember().getNickname()).
                build();
        this.comment = comment.getComment();
        this.dateCreated = comment.getDateCreated();
        this.commentId = comment.getCommentId();
        this.commentId = comment.getCommentId();
    }
}
