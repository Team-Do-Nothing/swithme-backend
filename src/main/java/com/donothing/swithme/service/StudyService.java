package com.donothing.swithme.service;

import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.dto.study.StudyDetailResponseDto;
import com.donothing.swithme.dto.study.StudyRegisterRequestDto;
import com.donothing.swithme.dto.study.StudyRegisterResponseDto;
import com.donothing.swithme.repository.StudyRepository;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    @Transactional
    public StudyRegisterResponseDto registerStudy(StudyRegisterRequestDto request) {
        Study study = studyRepository.save(request.toEntity());
        return new StudyRegisterResponseDto(study.getStudyId());
    }

    public StudyDetailResponseDto detailStudyByStudyId(String studyId) {
        Study study = studyRepository.findById(Long.parseLong(studyId)).orElseThrow(() -> {
            log.error("존재하지 않는 스터디 입니다. studyId = " + studyId);
            return new NoSuchElementException("존재하지 않는 스터디 입니다. ");
        });

        return new StudyDetailResponseDto(study);
    }

//    public Page<Study> getAllStudies(Pageable pageable) {
//        return studyRepository.findStudy(pageable);
//    }
}
