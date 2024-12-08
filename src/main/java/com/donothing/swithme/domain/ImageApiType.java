package com.donothing.swithme.domain;

public enum ImageApiType {
    STUDY, CHALLENGE, PROFILE;

    public static boolean isValid(String type) {
        try {
            ImageApiType.valueOf(type.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
