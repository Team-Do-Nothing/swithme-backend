package com.donothing.swithme.service;

import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.study.*;
import com.donothing.swithme.repository.StudyRepository;
import java.util.NoSuchElementException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        Study study = validationAndGetStudy(studyId);
        return new StudyDetailResponseDto(study);
    }

    public StudyDetailResponseDto updateStudy(String studyId, StudyUpdateReqeustDto reuqest) {
        Study study = validationAndGetStudy(studyId);
        study.update(reuqest);

        return null;
    }

    public Page<StudyDetailResponseDto> getStudies(StudySearchRequest condition, Pageable pageable) {
        return studyRepository.searchStudies(condition, pageable);
    }

    public StudyCommentListResponseDto getCommentList(String studyId) {
        validationAndGetStudy(studyId);

        
        return null;
    }

    public Study validationAndGetStudy(String studyId) {
        return studyRepository.findById(Long.parseLong(studyId)).orElseThrow(() -> {
            log.error("존재하지 않는 스터디 입니다. studyId = " + studyId);
            return new NoSuchElementException("존재하지 않는 스터디 입니다. ");
        });
    }
}
