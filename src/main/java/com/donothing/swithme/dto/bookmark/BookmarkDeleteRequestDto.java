package com.donothing.swithme.dto.bookmark;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BookmarkDeleteRequestDto {
    @NotNull(message = "삭제할 북마크 아이디는 필수입니다.")
    private Long bookmarkId;
}
