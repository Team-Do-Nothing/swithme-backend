package com.donothing.swithme.dto.bookmark;

import lombok.Getter;

@Getter
public class BookmarkRegisterResponseDto {
    private final Long bookmarkId;

    public BookmarkRegisterResponseDto(Long bookmarkId) {
        this.bookmarkId = bookmarkId;
    }
}
