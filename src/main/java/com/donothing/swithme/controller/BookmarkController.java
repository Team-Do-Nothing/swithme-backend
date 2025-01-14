package com.donothing.swithme.controller;

import com.donothing.swithme.dto.bookmark.BookmarkDetailResponseDto;
import com.donothing.swithme.dto.bookmark.BookmarkRegisterRequestDto;
import com.donothing.swithme.dto.bookmark.BookmarkRegisterResponseDto;
import com.donothing.swithme.dto.bookmark.BookmarkSearchRequest;
import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.service.BookmarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/bookmark")
@RestController
@RequiredArgsConstructor
@Api(tags = {"북마크 관련 API"})
public class BookmarkController {

    private final BookmarkService bookmarkService;

    @GetMapping
    @ApiOperation(value = "나의 북마크 조회", notes = "나의 북마크 조회하는 API 입니다.")
    public Page<BookmarkDetailResponseDto> getBookmarks(
            BookmarkSearchRequest condition,
            @AuthenticationPrincipal UserDetails user) {
        if (user != null) condition.setMemberId(Long.valueOf(user.getUsername()));
        return bookmarkService.getBookmarks(condition, condition.toPageable());
    }
    @PostMapping
    @ApiOperation(value = "북마크 등록", notes = "북마크를 등록하는 API 입니다.")
    public ResponseEntity<ResponseDto<BookmarkRegisterResponseDto>> registerBookmark(@RequestBody @Valid
                                                                                        BookmarkRegisterRequestDto request,
                                                                                     @AuthenticationPrincipal UserDetails user) {
        request.setMemberId(Long.valueOf(user.getUsername()));
        return new ResponseEntity<>(new ResponseDto<>(201, "북마크 등록 성공",
                bookmarkService.registerBookmark(request)),
                HttpStatus.CREATED);
    }

    @DeleteMapping("{studyId}")
    @ApiOperation(value = "북마크 삭제", notes = "북마크를 삭제하는 API 입니다.")
    public ResponseEntity<ResponseDto<Void>> deleteBookmark(@PathVariable String studyId,
            @AuthenticationPrincipal UserDetails user) {
        bookmarkService.deleteBookmark(studyId, Long.valueOf(user.getUsername()));
        return new ResponseEntity<>(new ResponseDto<>(200, "북마크 삭제 성공", null),
                HttpStatus.OK);
    }
}
