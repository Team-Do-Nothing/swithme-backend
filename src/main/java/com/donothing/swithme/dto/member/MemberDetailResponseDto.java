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
public class MemberDetailResponseDto {

    private String nickname;
    private GenderType gender;
    private LocalDate birthdate;
    private String introduce;
    private Double temperature;
    
//    public MemberDetailResponseDto(Member member) {
//        this.nickname = member.getNickname();
//        this.gender = member.getGender();
//        this.birthdate = member.getBirthdate();
//        this.introduce = member.getIntroduce();
//        this.temperature = member.getTemperature();
//    }
}
