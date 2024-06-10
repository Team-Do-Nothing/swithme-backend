package com.donothing.swithme.repository;

import com.donothing.swithme.domain.Challenge;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeRepository extends JpaRepository<Challenge, Long> {

}
