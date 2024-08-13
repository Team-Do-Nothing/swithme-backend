package com.donothing.swithme.repository;

import com.donothing.swithme.domain.Challenge;
import com.donothing.swithme.domain.ChallengeLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChallengeLogRepository extends JpaRepository<ChallengeLog, Long> {

    //ChallengeLog findByMemberChallenge_MemberChallengeId(Long memberChallengeId);
    List<ChallengeLog> findAllByChallenge_ChallengeId(Long challengeId);
}
