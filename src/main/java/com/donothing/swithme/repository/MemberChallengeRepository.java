package com.donothing.swithme.repository;

import com.donothing.swithme.domain.MemberChallenge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberChallengeRepository extends JpaRepository<MemberChallenge, Long> {

//    List<MemberChallenge> findByMember_MemberId(long username);

    @Query("select mc from MemberChallenge mc"
            + "         join fetch mc.challenge ch"
            + "         join fetch mc.member m"
            + "         join fetch ch.study s"
            + "         where mc.member.memberId = :memberId"
            + "         and ch.study.studyId = :studyId"
            + "         ")
    List<MemberChallenge> fetchJoin(@Param("memberId") long memberId, @Param("studyId") long studyId);
}

