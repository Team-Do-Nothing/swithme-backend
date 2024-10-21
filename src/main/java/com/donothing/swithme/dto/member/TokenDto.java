package com.donothing.swithme.dto.member;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private String grantType;
    private String memberId;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiresIn;
}
