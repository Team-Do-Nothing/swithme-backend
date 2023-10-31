package com.donothing.swithme.controller;

import com.donothing.swithme.dto.StudyRegisterRequest;
import com.donothing.swithme.dto.StudyRegisterResponse;
import com.donothing.swithme.service.StudyService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/study")
@RequiredArgsConstructor
public class StudyController {

    private final StudyService studyService;

    @PostMapping
    public ResponseEntity<StudyRegisterResponse> registerStudy(@RequestBody @Valid StudyRegisterRequest request) {
        return new ResponseEntity<>(studyService.registerStudy(request), HttpStatus.CREATED);
    }
}
