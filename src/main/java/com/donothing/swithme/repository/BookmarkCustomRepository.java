package com.donothing.swithme.repository;

import com.donothing.swithme.dto.bookmark.BookmarkDetailResponseDto;
import com.donothing.swithme.dto.bookmark.BookmarkSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookmarkCustomRepository  {
    Page<BookmarkDetailResponseDto> searchBookmarks(BookmarkSearchRequest condition, Pageable toPageable);
}
