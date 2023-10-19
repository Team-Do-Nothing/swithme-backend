package com.donothing.swithme.service;

import com.donothing.swithme.domain.Member;
import com.donothing.swithme.dto.member.MemberJoinDto;
import com.donothing.swithme.dto.member.MemberJoinResponseDto;
import com.donothing.swithme.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;

    /**
     * 자체 회원가입
     */
    @Transactional
    public MemberJoinResponseDto signup(MemberJoinDto memberJoinDto) {
        if (memberRepository.existsByEmail(memberJoinDto.getEmail())) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        Member member = memberJoinDto.toMember(memberJoinDto.getPassword());
        Member saveMember = memberRepository.save(member);

        return MemberJoinResponseDto.of(saveMember);
    }
}
