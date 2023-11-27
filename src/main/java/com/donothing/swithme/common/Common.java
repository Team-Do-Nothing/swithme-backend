package com.donothing.swithme.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Common {
    public static LocalDateTime formatLocalDateTime(String dateString) {
        if (dateString.isBlank()) return null;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDateTime.parse(dateString, formatter);
    }
}
