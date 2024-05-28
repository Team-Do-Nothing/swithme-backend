package com.donothing.swithme.service;

import com.donothing.swithme.domain.Challenge;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.challenge.ChallengeRegisterRequestDto;
import com.donothing.swithme.dto.challenge.ChallengeRegisterResponseDto;
import com.donothing.swithme.repository.ChallengeRepository;
import com.donothing.swithme.repository.MemberChallengeRepository;
import com.donothing.swithme.repository.StudyRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final MemberChallengeRepository memberChallengeRepository;
    private final StudyRepository studyRepository;

    public ChallengeRegisterResponseDto registChallenge(ChallengeRegisterRequestDto request) {
        Study study = studyRepository.findById(request.getStudyId()).orElseThrow(() -> {
            log.error("존재하지 않는 스터디 입니다. studyId = " + request.getStudyId());
            return new NoSuchElementException("존재하지 않는 스터디 입니다. ");
        });

        if (request.getMemberId() != (study.getMember().getMemberId()))
             throw new IllegalStateException("스터디 방장만 챌린지를 개설할 수 있습니다. ");

        validationDate(request.getStartDate());

        Challenge challenge = challengeRepository.save(request.toEntity());
        memberChallengeRepository.save(request.toMemberChallenge(challenge.getChallengeId()));

        return new ChallengeRegisterResponseDto(challenge.getChallengeId());
    }

    public boolean validationDate(String startDate) {
        try {
            LocalDate today = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDate inputDate = LocalDate.parse(startDate, formatter);

            return inputDate.isAfter(today);
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
