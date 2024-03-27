package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.StudyStatus;
import com.donothing.swithme.domain.StudyType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudyUpdateRequestDto {
    @NotNull(message = "제목 값은 필수입니다.")
    private String title;

    @NotNull(message = "스터디 타입 값은 필수입니다.")
    @ApiModelProperty(value = "스터디 타입", required = true, allowableValues = "ONLINE, OFFLINE")
    private StudyType studyType;

    @NotNull(message = "멤버 수 값은 필수입니다.")
    @Min(1)
    @ApiModelProperty(value = "멤버 수", required = true)
    private int numberOfMembers;

    @NotNull(message = "스터디 정보가 null값일 수 없습니다.")
    @ApiModelProperty(value = "스터디 정보", required = true)
    private String studyInfo;

    @NotNull(message = "스터디 상태 값은 필수입니다.")
    @ApiModelProperty(value = "스터디 상태", required = true, allowableValues = "CURR, COMP, END")
    private StudyStatus studyStatus;

    @NotNull(message = "스터디 시작날짜는 필수입니다.")
    @Pattern(regexp = "^\\d{8}$", message="스터디 시작일은 yyyyMMdd 형식의 날짜로 입력해주세요.")
    @ApiModelProperty(value = "스터디 시작날짜", required = true)
    private String dateStudyStart;

    @NotNull(message = "스터디 마감날짜는 필수입니다.")
    @Pattern(regexp = "^\\d{8}$", message="스터디 시작일은 yyyyMMdd 형식의 날짜로 입력해주세요.")
    @ApiModelProperty(value = "스터디 마감날짜", required = true)
    private String dateStudyEnd;

}
