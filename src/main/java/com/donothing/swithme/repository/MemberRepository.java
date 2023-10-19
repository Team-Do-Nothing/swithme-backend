package com.donothing.swithme.repository;

import com.donothing.swithme.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

//    Optional<Member> findByEmail(String email);

    /**
     * 이메일 중복 체크
     */
    boolean existsByEmail(String email);
}
