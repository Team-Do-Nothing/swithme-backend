package com.donothing.swithme.repository;

import com.donothing.swithme.domain.MemberChallenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberChallengeRepository extends JpaRepository<MemberChallenge, Long> {
    boolean existsByChallenge_ChallengeIdAndMember_MemberId(Long studyId, Long memberId);
}
