package com.donothing.swithme.service.member;

import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.MemberStudy;
import com.donothing.swithme.dto.member.MemberDetailResponseDto;
import com.donothing.swithme.dto.member.MemberListResponseDto;
import com.donothing.swithme.dto.member.MemberMyDetailResponseDto;
import com.donothing.swithme.dto.member.MemberUpdateDto;
import com.donothing.swithme.repository.MemberStudyRepository;
import com.donothing.swithme.repository.member.MemberRepository;
import com.donothing.swithme.service.StudyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberStudyRepository memberStudyRepository;
    private final StudyService studyService;

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

    public List<MemberListResponseDto> getMembers(String studyId) {
        // 스터디 아이디로 스터디 존재 여부 검증
        studyService.validationAndGetStudy(studyId);
        List<MemberStudy> allMembers = memberStudyRepository.findByStudy_StudyId(Long.valueOf(studyId));

        List<MemberListResponseDto> membersList = allMembers.stream()
                .map(MemberListResponseDto::new)
                .collect(Collectors.toList());

        return membersList;
    }

//    public MemberDetailResponseDto detailMemberByMemberId(Long memberId) {
//        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalStateException("멤버가 없습니다."));
//    }


}
