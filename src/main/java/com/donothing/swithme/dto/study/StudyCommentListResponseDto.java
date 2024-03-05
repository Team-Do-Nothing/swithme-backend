package com.donothing.swithme.dto.study;

import com.donothing.swithme.dto.member.MemberInfoResponseDto;
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
}
