package com.donothing.swithme.dto.study;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudySearchRequest {
    private String category;
    private String detailCategory;
    private String gu;
    private String dong;
    private String title;
}
