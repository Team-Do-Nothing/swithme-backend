package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.Study;
import com.donothing.swithme.domain.StudyStatus;
import com.donothing.swithme.domain.StudyType;
import com.donothing.swithme.dto.member.MemberInfoResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class StudyDetailResponseDto {
    @ApiModelProperty(value = "스터디 아이디")
    private Long studyId;
    
    @ApiModelProperty(value = "스터디 생성자")
    private MemberInfoResponseDto createdMember;

    @ApiModelProperty(value = "스터디 제목")
    private String title;

    @ApiModelProperty(value = "스터디 타입")
    private StudyType studyType; // ONLINE, OFFLINE

    @ApiModelProperty(value = "목표 인원수")
    private int numberOfMembers; // 목표 인원수

    @ApiModelProperty(value = "남은 인원수")
    private int remainingNumber; // 참여 가능한 남은 인원수

    @ApiModelProperty(value = "스터디 정보")
    private String studyInfo;

    @ApiModelProperty(value = "스터디 상태")
    private StudyStatus studyStatus; // CURR, COMP, END

    @ApiModelProperty(value = "스터디 시작 날")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String dateStudyStart;

    @ApiModelProperty(value = "스터디 마감 날")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private String dateStudyEnd;

    public StudyDetailResponseDto(Study study) {
        this.studyId = study.getStudyId();
        this.createdMember = MemberInfoResponseDto.builder().
                memberId(study.getMember().getMemberId()).
                name(study.getMember().getName()).
                nickname(study.getMember().getNickname()).
                build();
        this.title = study.getTitle();
        this.studyType = study.getStudyType();
        this.numberOfMembers = study.getNumberOfMembers();
        this.remainingNumber = study.getRemainingNumber();
        this.studyInfo = study.getStudyInfo();
        this.studyStatus = study.getStudyStatus();
        this.dateStudyStart = study.getDateStudyStart();
        this.dateStudyEnd = study.getDateStudyEnd();
    }
}
