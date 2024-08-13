package com.donothing.swithme.repository;

import com.donothing.swithme.domain.Comment;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.study.StudyCommentListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
