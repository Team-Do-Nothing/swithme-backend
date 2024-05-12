package com.donothing.swithme.service.member;

import com.donothing.swithme.domain.Member;
import com.donothing.swithme.dto.member.MemberDetailResponseDto;
import com.donothing.swithme.dto.member.MemberMyDetailResponseDto;
import com.donothing.swithme.dto.member.MemberUpdateDto;
import com.donothing.swithme.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 멤버 정보 수정
     * @param memberId
     * @param memberUpdateDto
     */
    @Transactional
    public void updateMember(Long memberId, MemberUpdateDto memberUpdateDto) {

        Member member = findByMemberId(memberId);
        member.setPhone(memberUpdateDto.getPhone());
        member.setIntroduce(memberUpdateDto.getIntroduce());
    }

//    public MemberDetailResponseDto detailMemberByMemberId(Long memberId) {
//        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("멤버가 없습니다."));
//    }


}
