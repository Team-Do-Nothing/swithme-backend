package com.donothing.swithme.dto.study;

import lombok.Data;

@Data
public class StudyRegisterResponseDto {
    private Long studyId;

    public StudyRegisterResponseDto(Long studyId) {
        this.studyId = studyId;
    }
}
