package com.donothing.swithme.repository;

import static com.donothing.swithme.domain.QBookmark.bookmark;
import static com.donothing.swithme.domain.QStudy.study;

import com.donothing.swithme.domain.Bookmark;
import com.donothing.swithme.domain.QBookmark;
import com.donothing.swithme.dto.bookmark.BookmarkDetailResponseDto;
import com.donothing.swithme.dto.bookmark.BookmarkSearchRequest;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;

@Component
public class BookmarkCustomRepositoryImpl implements BookmarkCustomRepository{

    private final JPAQueryFactory queryFactory;

    public BookmarkCustomRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<BookmarkDetailResponseDto> searchBookmarks(BookmarkSearchRequest request, Pageable pageable) {
        QBookmark qBookmark = new QBookmark("bookmark");
//        OrderSpecifier<?> orderBy = request.getOrder() == SortOrder.DESC?
//                qBookmark.bookmarkId.desc() : qBookmark.bookmarkId.asc();

        List<Bookmark> bookmarkList =
                queryFactory.select(
                        bookmark
                ).from(bookmark)
                .join(bookmark.study, study).fetchJoin() // Fetch Join 추가
                .where(memberIdEq(request.getMemberId()))
                .orderBy(
                        qBookmark.bookmarkId.desc()
                ).fetch();

        JPAQuery<Long> countQuery = queryFactory.select(bookmark.count())
                .from(bookmark);

        List<BookmarkDetailResponseDto> result = bookmarkList.stream().map(
                bookmark ->
                        new BookmarkDetailResponseDto(bookmark.getBookmarkId(), bookmark.getStudy())).collect(
                Collectors.toList());

        return PageableExecutionUtils.getPage(result, pageable, countQuery::fetchOne);
    }
    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? bookmark.member.memberId.eq(memberId) : null;
    }
}
