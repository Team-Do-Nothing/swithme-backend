package com.donothing.swithme.domain;

import lombok.Getter;
import java.util.Arrays;

@Getter
public enum StudyCategory {
    PROGRAMMING(1, "프로그래밍"),
    WEB_DEVELOPMENT(5, "웹 개발"),
    MOBILE_APP_DEVELOPMENT(6, "모바일 앱 개발"),
    GAME_DEVELOPMENT(7, "게임 개발"),
    CODING_TEST(8, "코딩테스트"),

    CERTIFICATION(2, "자격증"),
    IT_CERT(9, "IT"),
    FINANCE_CERT(10, "금융"),
    HEALTH_CERT(11, "보건"),
    HISTORY_CERT(12, "역사"),
    ENGINEERING_CERT(13, "공학"),

    LANGUAGE(3, "어학"),
    ENGLISH(14, "영어"),
    CHINESE(15, "중국어"),
    JAPANESE(16, "일본어"),
    GERMAN(17, "독일어"),
    FRENCH(18, "프랑스어"),

    INTERVIEW(4, "면접"),
    PUBLIC_CORP_INTERVIEW(19, "공기업"),
    FINANCE_INTERVIEW(20, "금융권"),
    ENGINEERING_INTERVIEW(21, "공학계열"),
    LANGUAGE_INTERVIEW(22, "어문계열");

    private final int categoryId;
    private final String description;

    StudyCategory(int categoryId, String description) {
        this.categoryId = categoryId;
        this.description = description;
    }

    public static StudyCategory fromId(int categoryId) {
        return Arrays.stream(StudyCategory.values())
                .filter(category -> category.getCategoryId() == categoryId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("잘못된 카테고리 ID: " + categoryId));
    }
}
