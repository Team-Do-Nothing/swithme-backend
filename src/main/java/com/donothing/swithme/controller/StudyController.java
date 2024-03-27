package com.donothing.swithme.controller;

import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.dto.study.*;
import com.donothing.swithme.service.StudyService;
import java.util.List;
import javax.validation.Valid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/study")
@RequiredArgsConstructor
@Api(tags = {"스터디 관련 API"})
public class StudyController {

    private final StudyService studyService;

    @PostMapping("/register")
    @ApiOperation(value = "스터디 등록", notes = "스터디 등록하는 API 입니다.")
    public ResponseEntity<ResponseDto<StudyRegisterResponseDto>> registerStudy(@RequestBody @Valid
        StudyRegisterRequestDto request, @AuthenticationPrincipal UserDetails user) {
        request.setMemberId(Long.valueOf(user.getUsername()));
        return new ResponseEntity<>(new ResponseDto<>(201, "스터디 생성 성공",
                studyService.registerStudy(request)),
                HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "모든 스터디 조회", notes = "스터디를 조회하는 API 입니다.")
    public Page<StudyDetailResponseDto> getStudies(StudySearchRequest condition, Pageable pageable) {
        return studyService.getStudies(condition, pageable);
    }

    @GetMapping("/{studyId}")
    @ApiOperation(value = "상세 스터디 조회", notes = "해당 스터디의 정보를 조회하는 API 입니다.")
    public ResponseEntity<ResponseDto<StudyDetailResponseDto>> detailStudyByStudyId(@PathVariable String studyId) {
        return new ResponseEntity<>(new ResponseDto<>(200, "스터디 조회 성공",
                studyService.detailStudyByStudyId(studyId)),
                HttpStatus.OK);
    }

    @PutMapping("/{studyId}")
    @ApiOperation(value = "해당 스터디 수정", notes = "해당 스터디의 정보를 수정하는 API 입니다.")
    public ResponseEntity<ResponseDto<StudyDetailResponseDto>> updateStudy(@PathVariable String studyId,
            @RequestBody StudyUpdateRequestDto request) {
        return new ResponseEntity<>(new ResponseDto<>(201, "스터디 수정 성공",
                studyService.updateStudy(studyId, request)),
                HttpStatus.OK);
    }

    @GetMapping("/comment/{studyId}")
    @ApiOperation(value = "해당 스터디 댓글 조회", notes = "해당 스터디의 댓글을 조회하는 API 입니다.")
    public ResponseEntity<ResponseDto<List<StudyCommentListResponseDto>>> getCommentList(@PathVariable String studyId) {
        return new ResponseEntity<>(new ResponseDto<>(200, "해당 스터디 댓글 내용 조회 성공",
                studyService.getCommentList(studyId)),
                HttpStatus.OK);
    }
    @PostMapping("/comment/{studyId}")
    @ApiOperation(value = "해당 스터디에 댓글 등록", notes = "해당 스터디의 댓글을 등록하는 API 입니다.")
    public ResponseEntity<ResponseDto<Void>> comment(@PathVariable String studyId,
                                                    @RequestBody StudyCommentReqeustDto request,
                                                    @AuthenticationPrincipal UserDetails user) {
        request.setMemberId(Long.valueOf(user.getUsername()));
        studyService.comment(studyId, request);
        return new ResponseEntity<>(new ResponseDto<>(201, "스터디 댓글 달기 성공",
                null),
                HttpStatus.OK);
    }

    @PutMapping("/comment")
    @ApiOperation(value = "해당 댓글 수정", notes = "해당 댓글을 수정하는 API 입니다.")
    public ResponseEntity<ResponseDto<Void>> updateComment(@RequestBody StudyCommentUpdateRequestDto request) {
        studyService.updateComment(request);
        return new ResponseEntity<>(new ResponseDto<>(201, "스터디 댓글 수정 성공",
                 null),
                HttpStatus.OK);
    }

    @DeleteMapping("/comment/{commentId}")
    @ApiOperation(value = "해당 스터디에 댓글 삭제", notes = "해당 스터디에 댓글을 삭제할 수 있는 API 입니다.")
    public ResponseEntity<ResponseDto<Void>> deleteComment(@PathVariable String commentId) {
        studyService.deleteComment(Long.valueOf(commentId));
        return new ResponseEntity<>(new ResponseDto<>(201, "스터디 댓글 삭제 성공",
                null),
                HttpStatus.OK);
    }
}
