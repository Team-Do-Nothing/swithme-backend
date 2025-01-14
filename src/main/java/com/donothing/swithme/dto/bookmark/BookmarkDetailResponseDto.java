package com.donothing.swithme.dto.bookmark;

import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.study.StudyDetailResponseDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@Getter
public class BookmarkDetailResponseDto {

    @ApiModelProperty(value = "북마크 아이디")
    private long bookmarkId;

    @ApiModelProperty(value = "스터디")
    private StudyDetailResponseDto study;

    public BookmarkDetailResponseDto(long bookmarkId, Study study) {
        this.bookmarkId = bookmarkId;
        this.study = new StudyDetailResponseDto(study, 0);
    }
}
