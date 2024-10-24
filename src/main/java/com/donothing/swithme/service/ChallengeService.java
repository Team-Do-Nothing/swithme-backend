package com.donothing.swithme.service;

import com.donothing.swithme.domain.*;
import com.donothing.swithme.dto.JoinChallengeRequestDto;
import com.donothing.swithme.dto.challenge.ChallengeCertifyRequestDto;
import com.donothing.swithme.dto.challenge.ChallengeDetailResponseDto;
import com.donothing.swithme.dto.challenge.ChallengeRegisterRequestDto;
import com.donothing.swithme.dto.challenge.ChallengeRegisterResponseDto;
import com.donothing.swithme.repository.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.donothing.swithme.service.s3.S3Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;

import static com.donothing.swithme.domain.ApproveStatus.*;
import static com.donothing.swithme.domain.ChallengeLogStatus.ACTIVE;
import static com.donothing.swithme.domain.ChallengeLogStatus.INACTIVE;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChallengeService {
    private final ChallengeRepository challengeRepository;
    private final MemberChallengeRepository memberChallengeRepository;
    private final StudyRepository studyRepository;
    private final ChallengeLogRepository challengeLogRepository;
    private final MemberStudyRepository memberStudyRepository;
    private final S3Service s3Service;

    @Transactional
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
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate inputDate = LocalDate.parse(startDate, formatter);

            return inputDate.isAfter(today);
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public ChallengeDetailResponseDto detailChallengeByChallengeId(String challengeId) {
        Challenge challenge = validateChallenge(Long.parseLong(challengeId));

        return new ChallengeDetailResponseDto(challenge);
    }

    @Transactional
    public void joinChallenge(JoinChallengeRequestDto request) {
        Challenge challenge = validateChallenge(request.getChallengeId());

        // 스터디원인지 체크
        if (!memberStudyRepository.existsByStudy_StudyIdAndMember_MemberId(challenge.getStudy().getStudyId(),
                request.getMemberId())) {
            log.error("스터디에 참가한 유저만 챌린지에 참여할 수 있습니다.");
            throw new IllegalStateException("스터디에 참가한 유저만 챌린지에 참여할 수 있습니다.");
        }

        // MemberChallenge 조회 해서 memberId 있는지 체크
        if(memberChallengeRepository.existsByChallenge_ChallengeIdAndMember_MemberId(challenge.getChallengeId(),
                request.getMemberId())) {
            log.error("이미 참여하고 있는 유저입니다.");
            throw new IllegalStateException("이미 참여하고 있는 유저입니다.");
        }


        memberChallengeRepository.save(request.toMemberChallenge());
    }

    public Challenge validateChallenge(Long challengeId) {
        return challengeRepository.findById(challengeId).orElseThrow(() -> {
            log.error("존재하지 않는 챌린지 입니다. challengeId = " + challengeId);
            return new NoSuchElementException("존재하지 않는 챌린지 입니다.");
        });
    }

    public List<ChallengeDetailResponseDto> getMyChallenge(String username, String studyId) {
        List<MemberChallenge> challenges = memberChallengeRepository.fetchJoin(Long.parseLong(username), Long.parseLong(studyId));
        return challenges.stream().map(ChallengeDetailResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void updateChallengeLogStatusToInactive(Long challengeId, Long loginId) {
        // 1. 챌린지 검증
        Challenge challenge = validateChallenge(challengeId);

        // 2. 챌린지 개설자(방장) 검증
        if (loginId != (challenge.getStudy().getMember().getMemberId())) {
            throw new IllegalStateException("스터디 방장만 챌린지를 삭제할 수 있습니다.");
        }

        // 3. 챌린지 인증 내역 비활성화 처리
        List<ChallengeLog> challengeLogList = challengeLogRepository.findAllByChallenge_ChallengeId(challengeId);

        for (ChallengeLog challengeLog : challengeLogList) {
            if (challengeLog == null) continue;

            if (challengeLog.getChallengeLogStatus().equals(ACTIVE)) {
                challengeLog.setChallengeLogStatus(INACTIVE);
            }
        }
        challengeLogRepository.saveAll(challengeLogList);

        // 4. 챌린지 관련 연관관계 삭제
        // 챌린지 삭제
        challengeRepository.deleteById(challengeId);
        // TODO 챌린지 내 멤버들 연관관계 삭제
        //memberChallengeRepository.deleteAll();
    }

    @Transactional
    public void certifyChallenge(MultipartFile file, ChallengeCertifyRequestDto certifyRequestDto)
            throws IOException {
        // 1. 챌린지 검증
        Challenge challenge = validateChallenge(certifyRequestDto.getChallengeId());

        // 2. S3에 파일 업로드
        String fileUrl = s3Service.uploadFile(file, certifyRequestDto.getMemberId().toString());
        System.out.println(fileUrl);

        // 챌린지 인증내역 저장 (방장 자동 승인)
        // 3-1. 챌린지 개설자(방장) 검증
        if (certifyRequestDto.getMemberId() == (challenge.getStudy().getMember().getMemberId())) {
            challengeLogRepository.save(certifyRequestDto.toEntity(fileUrl, APPROVE));
        }
        else { // 3-2. 챌린지 인증내역 저장 (승인 대기 상태)
            challengeLogRepository.save(certifyRequestDto.toEntity(fileUrl, WAIT));
        }
    }

    @Transactional
    public void approveChallengeLog(Long loginId, String challengeLogId, String approveStatus) {
        // 1. 챌린지 유효성 검증
        ChallengeLog challengeLog = challengeLogRepository.findById(Long.parseLong(challengeLogId)).orElseThrow(() -> {
                    log.error("존재하지 않는 챌린지로그입니다. challengeLogId = " + challengeLogId);
                    return new NoSuchElementException("존재하지 않는 챌린지입니다.");});
        Challenge challenge = challengeLog.getChallenge();

        // 2. 챌린지 개설자(방장) 검증
        if (loginId != (challenge.getStudy().getMember().getMemberId())) {
            throw new IllegalStateException("챌린지 개설자만 챌린지 인증을 승인할 수 있습니다.");
        }

        // 3. 챌린지 인증 도메인 승인/거절 (WAIT -> APPROVE/DENY)
        if (approveStatus.equals(APPROVE)){
            challengeLog.setChallengeLogApproveStatus(APPROVE);
        } else if (approveStatus.equals(DENY)) {
            challengeLog.setChallengeLogApproveStatus(DENY);
        } else {
            throw new IllegalStateException("유효하지 않은 상태 코드값입니다.");
        }
    }
}
