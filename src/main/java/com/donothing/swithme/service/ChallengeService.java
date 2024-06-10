package com.donothing.swithme.service;

import com.donothing.swithme.domain.Challenge;
import com.donothing.swithme.domain.MemberChallenge;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.challenge.ChallengeDetailResponseDto;
import com.donothing.swithme.dto.challenge.ChallengeRegisterRequestDto;
import com.donothing.swithme.dto.challenge.ChallengeRegisterResponseDto;
import com.donothing.swithme.repository.ChallengeRepository;
import com.donothing.swithme.repository.MemberChallengeRepository;
import com.donothing.swithme.repository.StudyRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
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

    public ChallengeRegisterResponseDto registerChallenge(ChallengeRegisterRequestDto request) {
        Study study = studyRepository.findById(request.getStudyId()).orElseThrow(() -> {
            log.error("존재하지 않는 스터디 입니다. studyId = " + request.getStudyId());
            return new NoSuchElementException("존재하지 않는 스터디 입니다. ");
        });

        if (request.getMemberId() != (study.getMember().getMemberId()))
             throw new IllegalStateException("스터디 방장만 챌린지를 개설할 수 있습니다. ");

        if (!validationDate(request.getStartDate())) {
            throw new IllegalStateException("오늘 날짜 이후로 챌린지를 개설해야 합니다.");
        }

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

    public ChallengeDetailResponseDto detailChallengeByChallengeId(String challengeId) {
        Challenge challenge = challengeRepository.findById(Long.parseLong(challengeId)).orElseThrow(() -> {
            log.error("존재하지 않는 챌린지 입니다. challengeId = " + challengeId);
            return new NoSuchElementException("존재하지 않는 챌린지 입니다.");
        });

        return new ChallengeDetailResponseDto(challenge);
    }

    public List<ChallengeDetailResponseDto> getMyChallenge(String username, String studyId) {
        List<MemberChallenge> challenges = memberChallengeRepository.fetchJoin(Long.parseLong(username), Long.parseLong(studyId));
        return challenges.stream().map(ChallengeDetailResponseDto::new).collect(Collectors.toList());
    }
}
