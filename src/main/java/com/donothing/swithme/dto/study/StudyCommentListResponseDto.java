package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.Comment;
import com.donothing.swithme.dto.member.MemberInfoResponseDto;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateCreated;
    private boolean isDeleted;
    private List<StudyCommentListResponseDto> recomment;

    public StudyCommentListResponseDto(Comment comment, List<Comment> allComments) {
        List<StudyCommentListResponseDto> recomment = new ArrayList<>(); // 대댓글을 담을 리스트

        this.commentId = comment.getCommentId();
        this.memberInfo = MemberInfoResponseDto.builder().
                memberId(comment.getMember().getMemberId()).
                name(comment.getMember().getName()).
                nickname(comment.getMember().getNickname()).
                build();
        this.comment = comment.getComment();
        this.dateCreated = comment.getDateCreated();
        this.commentId = comment.getCommentId();

        for (Comment c : allComments) {
            if (c.getCommentTag().equals(comment.getCommentId())) {
                recomment.add(new StudyCommentListResponseDto(c));
            }
        }

        this.recomment = recomment;
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
    }
}
