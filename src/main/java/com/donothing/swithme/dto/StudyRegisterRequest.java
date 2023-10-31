package com.donothing.swithme.dto;

import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.domain.StudyType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StudyRegisterRequest {
    @NotNull(message = "카테고리 아이디는 필수입니다.")
    private Long categoryId;

    @NotNull(message = "멤버 아이디 값은 필수입니다.")
    private Long memberId;

    @NotNull(message = "제목은 필수입니다.")
    private String title;

    @NotNull(message = "스터디 타입은 필수입니다.")
    private StudyType studyType;

    @NotNull(message = "멤버 수 값은 필수입니다.")
    @Min(1)
    private int numberOfMembers;

    @NotNull(message = "지역 코드는 필수입니다.")
    private String regionCode;

    @NotNull(message = "스터디 정보는 필수입니다.")
    private String studyInfo;

    public Study toEntity() {
        return Study.builder()
                .member(new Member(memberId))
                .title(title)
                .studyType(studyType)
                .numberOfMembers(numberOfMembers)
                .studyInfo(studyInfo)
                .build();
    }
}
