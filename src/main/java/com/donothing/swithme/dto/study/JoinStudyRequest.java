package com.donothing.swithme.dto.study;

import com.donothing.swithme.domain.ApproveStatus;
import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.MemberStudy;
import com.donothing.swithme.domain.Study;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinStudyRequest {
    @ApiModelProperty(hidden = true)
    private Long studyId;

    @ApiModelProperty(hidden = true)
    private Long memberId;

    @NotNull(message = "승인시켜 줄 회원아이디.")
    @ApiModelProperty(value = "1", required = true)
    private Long requestMemberId;

    public MemberStudy toEntity(ApproveStatus approveStatus) {
        return MemberStudy.builder()
                .study(new Study(studyId))
                .member(new Member(memberId))
                .approveStatus(approveStatus)
                .build();
    }
}
