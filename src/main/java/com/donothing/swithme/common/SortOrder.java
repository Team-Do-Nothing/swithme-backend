package com.donothing.swithme.common;

public enum SortOrder {
    ASC("오름차순"),
    DESC("내림차순");

    private final String description;

    SortOrder(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
