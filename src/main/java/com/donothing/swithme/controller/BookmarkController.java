package com.donothing.swithme.controller;

import com.donothing.swithme.dto.bookmark.BookmarkDeleteRequestDto;
import com.donothing.swithme.dto.bookmark.BookmarkRegisterRequestDto;
import com.donothing.swithme.dto.bookmark.BookmarkRegisterResponseDto;
import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.dto.study.StudyRegisterRequestDto;
import com.donothing.swithme.dto.study.StudyRegisterResponseDto;
import com.donothing.swithme.service.BookmarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/v1/bookmark")
@RestController
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkService bookmarkService;
    @PostMapping
    public ResponseEntity<ResponseDto<BookmarkRegisterResponseDto>> registerBookmark(@RequestBody @Valid
                                                                                  BookmarkRegisterRequestDto request) {
        return new ResponseEntity<>(new ResponseDto<>(201, "북마크 등록 성공",
                bookmarkService.registerBookmark(request)),
                HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto<Void>> deleteBookmark(@RequestBody @Valid
                                                                BookmarkDeleteRequestDto request) {
        bookmarkService.deleteBookmark(request);
        return new ResponseEntity<>(new ResponseDto<>(200, "북마크 삭제 성공", null),
                HttpStatus.OK);
    }
}
