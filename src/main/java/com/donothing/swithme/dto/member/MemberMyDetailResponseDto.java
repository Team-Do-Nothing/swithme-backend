package com.donothing.swithme.dto.member;

import com.donothing.swithme.domain.GenderType;
import com.donothing.swithme.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberMyDetailResponseDto {

    private Long memberId;
    private String email;
    private String name;
    private String nickname;
    private GenderType gender;
    private LocalDate birthdate;
    private String phone;
    private String introduce;
    private Double temperature;

//    public static MemberMyDetailResponseDto of(Member member) {
//        return new MemberMyDetailResponseDto(member.getEmail());
//    }
}
