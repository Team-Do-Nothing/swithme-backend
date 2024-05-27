package com.donothing.swithme.service;

import com.donothing.swithme.domain.Challenge;
import com.donothing.swithme.dto.challenge.ChallengeRegisterRequestDto;
import com.donothing.swithme.dto.challenge.ChallengeRegisterResponseDto;
import com.donothing.swithme.repository.ChallengeRepository;
import com.donothing.swithme.repository.MemberChallengeRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final MemberChallengeRepository memberChallengeRepository;

    public ChallengeRegisterResponseDto registChallenge(ChallengeRegisterRequestDto request) {
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
