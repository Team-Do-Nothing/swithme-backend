package com.donothing.swithme.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberNickNameDuplicateDto {

    @NotBlank
    private String nickname;
}
