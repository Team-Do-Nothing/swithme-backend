package com.donothing.swithme.repository;

import com.donothing.swithme.domain.MemberStudy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberStudyRepository extends JpaRepository<MemberStudy, Long> {
    boolean existsByStudy_StudyIdAndMember_MemberId(Long studyId, Long memberId);

    List<MemberStudy> findByStudy_StudyId(Long studyId);
}
