package com.donothing.swithme.dto.bookmark;

import com.donothing.swithme.domain.Bookmark;
import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.Study;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BookmarkRegisterRequestDto {
    @NotNull(message = "북마크로 등록 될 스터디 아이디는 필수입니다.")
    private Long studyId;

    // TODO: 안보이게 설정하기
    private Long memberId;

    public Bookmark toEntity() {
        return Bookmark.builder()
                .member(new Member(memberId))
                .study(new Study(studyId))
                .build();
    }
}
