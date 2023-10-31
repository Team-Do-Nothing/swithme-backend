package com.donothing.swithme.service;

import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.StudyRegisterRequest;
import com.donothing.swithme.dto.StudyRegisterResponse;
import com.donothing.swithme.repository.StudyRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    @Transactional
    public StudyRegisterResponse registerStudy(StudyRegisterRequest request) {
        Study study = studyRepository.save(request.toEntity());
        return new StudyRegisterResponse(study.getStudyId());
    }
}
