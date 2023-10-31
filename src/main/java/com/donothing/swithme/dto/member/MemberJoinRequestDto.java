package com.donothing.swithme.dto.member;

import com.donothing.swithme.domain.GenderType;
import com.donothing.swithme.domain.LoginType;
import com.donothing.swithme.domain.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberJoinRequestDto {

    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "이메일 양식을 지켜주세요.")
    private String email;

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Pattern(regexp = "^(?=.*[A-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-z\\d$@$!%*#?&]{6,20}$",
            message = "비밀번호는 6~20자리수여야 합니다. 영문, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;

    @NotBlank(message = "비밀번호 재입력은 필수 입력입니다.")
    private String passwordConfirm;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 2, max = 16, message = "닉네임은 2글자 이상 16글자 이하여야 합니다.")
    private String nickname;

    @NotNull(message = "성별은 필수 입력입니다.")
    private GenderType gender;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate birthdate;

    @NotBlank
    private String phone;

    private String introduce;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(this.email)
                .password(passwordEncoder.encode(password))
                .name(this.name)
                .nickname(this.nickname)
                .loginType(LoginType.NORMAL)
                .gender(this.gender)
                .birthdate(this.birthdate)
                .phone(this.phone)
                .introduce(this.introduce)
                .build();
    }
}
