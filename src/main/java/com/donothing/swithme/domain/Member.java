package com.donothing.swithme.domain;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member extends BaseTimeEntity implements UserDetails {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(unique = true, nullable = false, length = 100)
    private String email; // 로그인 이메일

    @Column(nullable = false)
    private String password; // 로그인 비밀번호

    @Column(nullable = false, length = 100)
    private String name;

    @Column(unique = true, nullable = false, length = 100)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private LoginType loginType; // NORMAL, GOOGLE, KAKAO, NAVER

    private String oauthId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GenderType gender; // M, F

    @Column(nullable = false)
    private LocalDate birthdate;

    @Column(nullable = false, length = 100)
    private String phone;

    @Column(columnDefinition = "TEXT", length = 1000)
    private String introduce;

    private LocalDateTime dateWithdraw; // 멤버 탈퇴일자

    @Column(nullable = false)
    private Boolean withdraw;

    private Double temperature;

    /**
     * 검성 코드 ====
     */
    @Column
//    @Enumerated()
    private String authority;

    /*
    검성 코드 =====
     */

    // 자체 회원가입
    @Builder
    public Member(String email, String password, String name, String nickname, LoginType loginType, GenderType gender,
                  LocalDate birthdate, String phone, String introduce) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.loginType = loginType;
        this.gender = gender;
        this.birthdate = birthdate;
        this.phone = phone;
        this.introduce = introduce;
        this.withdraw = false;
        this.temperature = 36.5;
    }

    public Member(Long memberId) {
        this.memberId = memberId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(authority));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
