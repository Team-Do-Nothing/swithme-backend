package com.donothing.swithme.service.member;

import com.donothing.swithme.domain.Member;
import com.donothing.swithme.dto.member.MemberDetailResponseDto;
import com.donothing.swithme.dto.member.MemberMyDetailResponseDto;
import com.donothing.swithme.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /**
     * 닉네임 중복체크
     */
    public boolean existsByNickName(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    /**
     * 멤버 조회
     */
    public Member findByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("멤버가 없습니다."));
    }

//    public MemberDetailResponseDto detailMemberByMemberId(Long memberId) {
//        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("멤버가 없습니다."));
//    }


}
