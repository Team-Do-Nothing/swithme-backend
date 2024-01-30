package com.donothing.swithme.repository;

import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.study.StudySearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudyCustomRepository {
    Page<Study> searchStudies(StudySearchRequest request, Pageable pageable);
}
