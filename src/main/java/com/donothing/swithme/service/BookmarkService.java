package com.donothing.swithme.service;

import com.donothing.swithme.domain.Bookmark;
import com.donothing.swithme.dto.bookmark.BookmarkDeleteRequestDto;
import com.donothing.swithme.dto.bookmark.BookmarkRegisterRequestDto;
import com.donothing.swithme.dto.bookmark.BookmarkRegisterResponseDto;
import com.donothing.swithme.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;

    public BookmarkRegisterResponseDto registerBookmark(BookmarkRegisterRequestDto request) {
        Bookmark bookmark = bookmarkRepository.save(request.toEntity());
        return new BookmarkRegisterResponseDto(bookmark.getBookmarkId());
    }

    public void deleteBookmark(BookmarkDeleteRequestDto request) {
        Bookmark bookmark = bookmarkRepository.findById(request.getBookmarkId()).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 북마크 아이디입니다."));
        bookmarkRepository.delete(bookmark);
    }
}
