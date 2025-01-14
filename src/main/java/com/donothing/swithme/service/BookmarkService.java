package com.donothing.swithme.service;

import com.donothing.swithme.domain.Bookmark;
import com.donothing.swithme.dto.bookmark.BookmarkDetailResponseDto;
import com.donothing.swithme.dto.bookmark.BookmarkRegisterRequestDto;
import com.donothing.swithme.dto.bookmark.BookmarkRegisterResponseDto;
import com.donothing.swithme.dto.bookmark.BookmarkSearchRequest;
import com.donothing.swithme.repository.BookmarkCustomRepository;
import com.donothing.swithme.repository.BookmarkRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkService {
    private final BookmarkRepository bookmarkRepository;
    private final BookmarkCustomRepository bookmarkCustomRepository;

    @Transactional
    public BookmarkRegisterResponseDto registerBookmark(BookmarkRegisterRequestDto request) {
        bookmarkRepository.
            findByStudy_StudyIdAndMember_MemberId(request.getStudyId(), request.getMemberId())
                .ifPresent(value -> {
                    throw new NoSuchElementException("이미 등록한 북마크입니다.");
                });

        Bookmark bookmark = bookmarkRepository.save(request.toEntity());

        return new BookmarkRegisterResponseDto(bookmark.getBookmarkId());
    }

    @Transactional
    public void deleteBookmark(String studyId, Long memberId) {
        Bookmark bookmark = bookmarkRepository.
                findByStudy_StudyIdAndMember_MemberId(Long.parseLong(studyId), memberId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 북마크입니다."));
        bookmarkRepository.delete(bookmark);
    }

    public Page<BookmarkDetailResponseDto> getBookmarks(BookmarkSearchRequest condition, Pageable toPageable) {
        return bookmarkCustomRepository.searchBookmarks(condition, toPageable);
    }
}
