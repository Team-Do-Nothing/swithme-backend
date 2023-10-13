package com.donothing.swithme.domain;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Member {
    @Id @GeneratedValue
    private Long memberId;
    private String email;
    private String password;
    private String name;
    private String nickname;
    @Enumerated(EnumType.STRING)
    private LoginType loginType;
    private String oauthId;
    @Enumerated(EnumType.STRING)
    private GenderType gender;
    private LocalDateTime birthdate;
    private String phone;
    private String introduce;
    private LocalDateTime dateCreated;
    private LocalDateTime dateLastLogin;
    private LocalDateTime dateWithdraw;
    private boolean withdraw;
    private double temperature;
}
