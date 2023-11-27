package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.StudyStatus;
import com.donothing.swithme.domain.StudyType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudyUpdateReqeustDto {
    @NotBlank(message = "제목 값은 필수입니다.")
    private String title;

    @NotBlank(message = "스터디 타입 값은 필수입니다.")
    private StudyType studyType;

    @NotBlank(message = "스터디원 수는 필수입니다.")
    private int numberOfMembers;

    @NotNull(message = "스터디 정보가 null값일 수 없습니다.")
    private String studyInfo;

    @NotBlank(message = "스터디 타입 값은 필수입니다.")
    private StudyStatus studyStatus;

    @Pattern(regexp = "^\\d{8}$", message="스터디 시작일은 yyyyMMdd 형식의 날짜로 입력해주세요.")
    private String dateStudyStart;

    @Pattern(regexp = "^\\d{8}$", message="스터디 종료일은 yyyyMMdd 형식의 날짜로 입력해주세요.")
    private String dateStudyEnd;
}
