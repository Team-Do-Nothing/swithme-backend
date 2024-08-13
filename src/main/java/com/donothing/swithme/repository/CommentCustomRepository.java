package com.donothing.swithme.repository;

import com.donothing.swithme.dto.study.StudyCommentListResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentCustomRepository {
//    List<Comment> findByStudy_StudyId(Long studyId);

    Page<StudyCommentListResponseDto> findByComment(Long studyId, Pageable pageable);
}
