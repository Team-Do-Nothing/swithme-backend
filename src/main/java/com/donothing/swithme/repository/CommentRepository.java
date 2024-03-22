package com.donothing.swithme.repository;

import com.donothing.swithme.domain.Comment;
import com.donothing.swithme.domain.Study;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByStudy_StudyId(Long studyId);
}
