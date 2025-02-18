package com.donothing.swithme.domain;

public enum StudyRole {
    LEADER("리더"),
    STUDY_MEMBER("스터디원");

    private final String description;

    StudyRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static StudyRole fromString(String text) {
        for (StudyRole role : StudyRole.values()) {
            if (role.description.equalsIgnoreCase(text) || role.name().equalsIgnoreCase(text)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
