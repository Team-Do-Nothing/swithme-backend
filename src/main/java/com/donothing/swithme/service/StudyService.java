package com.donothing.swithme.service;

import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.study.StudyRegisterRequestDto;
import com.donothing.swithme.dto.study.StudyRegisterResponseDto;
import com.donothing.swithme.repository.StudyRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    @Transactional
    public StudyRegisterResponseDto registerStudy(StudyRegisterRequestDto request) {
        Study study = studyRepository.save(request.toEntity());
        return new StudyRegisterResponseDto(study.getStudyId());
    }

//    public Page<Study> getAllStudies(Pageable pageable) {
//        return studyRepository.findStudy(pageable);
//    }
}
