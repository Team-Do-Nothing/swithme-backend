package com.donothing.swithme.dto.member;

import com.donothing.swithme.domain.GenderType;
import com.donothing.swithme.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinResponseDto {

    private Long memberId;
    private String name;
    private String nickname;
    private GenderType gender;
    private LocalDate birthdate;
    private String phone;
    private String introduce;

    public static MemberJoinResponseDto of (Member member) {
        return new MemberJoinResponseDto(member.getMemberId(),
                                        member.getName(),
                                        member.getNickname(),
                                        member.getGender(),
                                        member.getBirthdate(),
                                        member.getPhone(),
                                        member.getIntroduce());
    }
}
