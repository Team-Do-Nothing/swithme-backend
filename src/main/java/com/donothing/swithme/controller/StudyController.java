package com.donothing.swithme.controller;

import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.dto.study.StudyDetailResponseDto;
import com.donothing.swithme.dto.study.StudyRegisterRequestDto;
import com.donothing.swithme.dto.study.StudyRegisterResponseDto;
import com.donothing.swithme.dto.study.StudyUpdateReqeustDto;
import com.donothing.swithme.service.StudyService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<StudyRegisterResponseDto>> registerStudy(@RequestBody @Valid
        StudyRegisterRequestDto request) {
        return new ResponseEntity<>(new ResponseDto<>(201, "스터디 생성 성공",
                studyService.registerStudy(request)),
                HttpStatus.CREATED);
    }

    @GetMapping
    public void getStudies(Pageable pageable) {
//        studyService.getAllStudies(pageable);
//        return new ResponseEntity<>()
    }

    @GetMapping("/{studyId}")
    public ResponseEntity<ResponseDto<StudyDetailResponseDto>> detailStudyByStudyId(@PathVariable String studyId) {
        return new ResponseEntity<>(new ResponseDto<>(200, "스터디 조회 성공",
                studyService.detailStudyByStudyId(studyId)),
                HttpStatus.OK);
    }

    @PutMapping("/{studyId}")
    public ResponseEntity<ResponseDto<StudyDetailResponseDto>> updateStudy(@PathVariable String studyId,
            @RequestBody StudyUpdateReqeustDto reuqest) {
        return new ResponseEntity<>(new ResponseDto<>(201, "스터디 수정 성공",
                studyService.updateStudy(studyId, reuqest)),
                HttpStatus.OK);
    }

}
