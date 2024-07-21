package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.domain.StudyStatus;
import com.donothing.swithme.domain.StudyType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudyRegisterRequestDto {
    @NotNull(message = "카테고리 아이디는 필수입니다.")
    @ApiModelProperty(value = "카테고리 아이디", example = "1", required = true)
    private Long categoryId;

    @ApiModelProperty(hidden = true)
    private Long memberId;

    @NotNull(message = "제목은 필수입니다.")
    @ApiModelProperty(value = "제목", required = true)
    private String title;

    @NotNull(message = "스터디 타입은 필수입니다.")
    @ApiModelProperty(value = "스터디 타입", required = true, allowableValues = "ONLINE, OFFLINE")
    private StudyType studyType;

    @NotNull(message = "멤버 수 값은 필수입니다.")
    @Min(1)
    @ApiModelProperty(value = "멤버 수", required = true)
    private int numberOfMembers;

    @NotNull(message = "지역 코드는 필수입니다.")
    @ApiModelProperty(value = "지역 코드", example = "1", required = true)
    private String regionCode;

    @NotNull(message = "스터디 정보는 필수입니다.")
    @ApiModelProperty(value = "스터디 정보", required = true)
    private String studyInfo;

    @NotNull(message = "스터디 시작날짜는 필수입니다.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message="스터디 시작일은 yyyy-MM-dd 형식의 날짜로 입력해주세요.")
    @ApiModelProperty(value = "스터디 시작날짜", required = true, example = "2024-07-01")
    private String dateStudyStart;

    @NotNull(message = "스터디 마감날짜는 필수입니다.")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message="스터디 시작일은 yyyy-MM-dd 형식의 날짜로 입력해주세요.")
    @ApiModelProperty(value = "스터디 마감날짜", required = true,  example = "2024-07-01")
    private String dateStudyEnd;

    public Study toEntity() {
        return Study.builder()
                .member(new Member(memberId))
                .title(title)
                .studyType(studyType)
                .studyStatus(StudyStatus.CURR)
                .numberOfMembers(numberOfMembers)
                .remainingNumber(numberOfMembers - 1)
                .studyInfo(studyInfo)
                .dateStudyStart(dateStudyStart)
                .dateStudyEnd(dateStudyEnd)
                .build();
    }
}
