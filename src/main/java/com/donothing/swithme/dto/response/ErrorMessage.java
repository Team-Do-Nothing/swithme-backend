package com.donothing.swithme.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@RequiredArgsConstructor
public class ErrorMessage {
    private final HttpStatus code;
    private final String message;
}
