package com.donothing.swithme.config;

import com.donothing.swithme.config.jwt.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터체인에 등록됨
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // CSRF 설정 Disable
                .httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource())

                // exception handling 할 때 custom 클래스 추가
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                // Security 는 기본적으로 세션 사용
                // 여기서는 세션 사용하지 않으므로 세션 Stateless 로 설정
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // login, signup API 는 토큰이 없는 상태에서 요청이 들어오기 때문에 permitAll
                .and()
                .authorizeRequests()
                // TO DO : 배포 전에 URI 추가 설정 필요
//                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/**").permitAll()
                .antMatchers("/api/v1/**").permitAll()
//                .anyRequest().authenticated() // 나머지 API 는 전부 인증 필요

                // JwtFilter 를 addFilterBefore 로 등록했던 JwtSecurityConfig 클래스를 적용
                .and()
                .apply(new JwtSecurityConfig(jwtTokenProvider));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration = new CorsConfiguration();

//        configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:"));
//        configuration.addAllowedOrigin("*");        // 모든 ip에 응답
        configuration.addAllowedOrigin("http://localhost:3000");
        configuration.addAllowedHeader("*");        // 모든 header 에 응답
        configuration.addAllowedMethod("*");        // 모든 Method(GET,POST,PUT,DELETE 등) 요청 허용
        configuration.setAllowCredentials(true);    // 내 서버가 응답할 때 json을 자바스크립트에서 처리할 수 있게 할지 설정

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
