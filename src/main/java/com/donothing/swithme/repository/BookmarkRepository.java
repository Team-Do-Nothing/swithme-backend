package com.donothing.swithme.repository;

import com.donothing.swithme.domain.Bookmark;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Optional<Bookmark> findByStudy_StudyIdAndMember_MemberId(Long studyId, Long memberId);
}
