package com.donothing.swithme.dto.member;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class MemberUpdateDto {

    @NotBlank
    private String phone;

    @Size(max = 1000, message = "자기소개글은 1000자를 넘을 수 없습니다.")
    private String introduce;
}
