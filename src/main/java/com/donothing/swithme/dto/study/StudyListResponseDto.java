package com.donothing.swithme.dto.study;

import com.donothing.swithme.common.PagingData;
import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.domain.StudyStatus;
import com.donothing.swithme.domain.StudyType;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class StudyListResponseDto extends PagingData {
    List<StudyDetailResponseDto> contents;

    public StudyListResponseDto(Object data, int PageNumber, long totalCount) {
        super(data, PageNumber, totalCount);
    }
}
