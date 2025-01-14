package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.ApproveStatus;
import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.MemberStudy;
import com.donothing.swithme.domain.Study;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuitStudyRequest {

    @Setter
    @ApiModelProperty(hidden = true)
    private Long studyId;

    @ApiModelProperty(hidden = true)
    private Long memberId;

    public MemberStudy toEntity() {
        return MemberStudy.builder()
                .study(new Study(studyId))
                .member(new Member(memberId))
                .build();
    }
}
