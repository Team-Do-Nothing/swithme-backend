package com.donothing.swithme.controller;

import com.donothing.swithme.dto.bookmark.BookmarkDeleteRequestDto;
import com.donothing.swithme.dto.bookmark.BookmarkRegisterRequestDto;
import com.donothing.swithme.dto.bookmark.BookmarkRegisterResponseDto;
import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.service.BookmarkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/bookmark")
@RestController
@RequiredArgsConstructor
@Api(tags = {"북마크 관련 API"})
public class BookmarkController {

    private final BookmarkService bookmarkService;
    @PostMapping
    @ApiOperation(value = "북마크 등록", notes = "북마크를 등록하는 API 입니다.")
    public ResponseEntity<ResponseDto<BookmarkRegisterResponseDto>> registerBookmark(@RequestBody @Valid
                                                                                  BookmarkRegisterRequestDto request) {
        return new ResponseEntity<>(new ResponseDto<>(201, "북마크 등록 성공",
                bookmarkService.registerBookmark(request)),
                HttpStatus.CREATED);
    }

    @DeleteMapping
    @ApiOperation(value = "북마크 삭제", notes = "북마크를 삭제하는 API 입니다.")
    public ResponseEntity<ResponseDto<Void>> deleteBookmark(@RequestBody @Valid
                                                                BookmarkDeleteRequestDto request) {
        bookmarkService.deleteBookmark(request);
        return new ResponseEntity<>(new ResponseDto<>(200, "북마크 삭제 성공", null),
                HttpStatus.OK);
    }
}
