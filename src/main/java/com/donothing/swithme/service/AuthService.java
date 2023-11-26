package com.donothing.swithme.service;

import com.donothing.swithme.config.jwt.JwtTokenProvider;
import com.donothing.swithme.domain.Member;
import com.donothing.swithme.dto.member.MemberJoinRequestDto;
import com.donothing.swithme.dto.member.MemberJoinResponseDto;
import com.donothing.swithme.dto.member.MemberLoginRequestDto;
import com.donothing.swithme.dto.member.TokenDto;
import com.donothing.swithme.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * 자체 회원가입
     */
    @Transactional
    public MemberJoinResponseDto signup(MemberJoinRequestDto memberJoinRequestDto) {
        if (memberRepository.existsByEmail(memberJoinRequestDto.getEmail())) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        Member member = memberJoinRequestDto.toMember(passwordEncoder);
        Member saveMember = memberRepository.save(member);

        return MemberJoinResponseDto.of(saveMember);
    }

    /**
     * 로그인
     */
    @Transactional
    public TokenDto login(MemberLoginRequestDto loginRequestDto) throws RuntimeException {
        // 1. Login ID/PW 기반으로 AuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken = loginRequestDto.toAuthentication();

        Member member = memberRepository.findByEmail(loginRequestDto.getEmail())
                .orElseThrow(() -> new IllegalStateException("해당 사용자를 찾을 수 없습니다."));

        // 2. 실제로 검증 (사용자 비밀번호 체크)
        //    authenticate 메서드가 실행이 될 때 CustomUserDetailsService 에서 만들었던 loadUserByUsername 메서드가 실행됨
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // 3. 인증 정보 기반으로 JWT 토큰 생성
        TokenDto tokenDto = jwtTokenProvider.createTokenDto(authentication);

        // 4. 토큰 발급
        return tokenDto;
    }

    /**
     * 로그아웃
     */
//    @Transactional
//    public void logout(String accessToken) {
//        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
//        jwtProvider.logout(authentication.getName(), accessToken);
//    }

    /**
     * 닉네임 중복체크
     */
    public boolean existsByNickName(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }
}
