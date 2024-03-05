package com.donothing.swithme.controller;

import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.dto.study.*;
import com.donothing.swithme.service.StudyService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Page<Study> getStudies(StudySearchRequest condition, Pageable pageable) {
        return studyService.getStudies(condition, pageable);
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

    @GetMapping("/{studyId}")
    public ResponseEntity<ResponseDto<StudyCommentListResponseDto>> getCommentList(@PathVariable String studyId) {
        return new ResponseEntity<>(new ResponseDto<>(200, "해당 스터디 댓글 내용 조회 성공",
                studyService.getCommentList(studyId)),
                HttpStatus.OK);
    }
    @PostMapping("/comment/{studyId}")
    public ResponseEntity<ResponseDto<Void>> comment(@PathVariable String studyId,
                                                                           @RequestBody StudyCommentReqeustDto reuqest) {
//        studyService.comment(studyId, reuqest);
        return new ResponseEntity<>(new ResponseDto<>(201, "스터디 댓글 달기 성공",
                null),
                HttpStatus.OK);
    }

    @PutMapping("/comment/{studyId}")
    public ResponseEntity<ResponseDto<StudyDetailResponseDto>> updateComment(@PathVariable String studyId,
                                                                       @RequestBody StudyUpdateReqeustDto reuqest) {
        return new ResponseEntity<>(new ResponseDto<>(201, "스터디 댓글 달기 성공",
                studyService.updateStudy(studyId, reuqest)),
                HttpStatus.OK);
    }

    @DeleteMapping("/comment/{studyId}")
    public ResponseEntity<ResponseDto<StudyDetailResponseDto>> deleteComment(@PathVariable String studyId,
                                                                             @RequestBody StudyUpdateReqeustDto reuqest) {
        return new ResponseEntity<>(new ResponseDto<>(201, "스터디 댓글 달기 성공",
                studyService.updateStudy(studyId, reuqest)),
                HttpStatus.OK);
    }
}
