package com.donothing.swithme.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final JwtProvider jwtProvider;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 헤더에서 JWT를 받아옴
        String token = jwtProvider.resolveToken((HttpServletRequest) request);

        System.out.println("token >" + token);
        // 유효 토큰 검증
        if (token != null && jwtProvider.validateToken(token)) {
            System.out.println("if절 안입니다" + token);

            token = token.split(" ")[1].trim();

            // 토큰 유효 -> 토큰으로부터 유저 정보 가져옴
            Authentication authentication = jwtProvider.getAuthentication(token);
            // SecurityContext 에 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        System.out.println("if절 나왔어요");
        chain.doFilter(request, response);
    }
}
