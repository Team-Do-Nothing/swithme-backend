package com.donothing.swithme.service;

import com.donothing.swithme.domain.ApproveStatus;
import com.donothing.swithme.domain.Challenge;
import com.donothing.swithme.domain.Comment;
import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.MemberStudy;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.challenge.ChallengeDetailResponseDto;
import com.donothing.swithme.dto.study.*;
import com.donothing.swithme.repository.ChallengeRepository;
import com.donothing.swithme.repository.CommentCustomRepository;
import com.donothing.swithme.repository.CommentRepository;
import com.donothing.swithme.repository.MemberStudyRepository;
import com.donothing.swithme.repository.StudyRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;

    private final CommentCustomRepository commentCustomRepository;
    private final CommentRepository CommentRepository;

    private final MemberStudyRepository memberStudyRepository;

    private final ChallengeRepository challengeRepository;

    @Transactional
    public StudyRegisterResponseDto registerStudy(StudyRegisterRequestDto request) {
        Study study = studyRepository.save(request.toEntity());

        memberStudyRepository.save(MemberStudy.builder()
                        .study(study)
                        .member(new Member(request.getMemberId()))
                        .approveStatus(ApproveStatus.APPROVE)
                .build());

        return new StudyRegisterResponseDto(study.getStudyId());
    }

    public StudyDetailResponseDto detailStudyByStudyId(String studyId) {
        Study study = validationAndGetStudy(studyId);
        return new StudyDetailResponseDto(study);
    }
    @Transactional
    public void updateStudy(String studyId, StudyUpdateRequestDto request) {
        Study study = validationAndGetStudy(studyId);
        study.update(request);
    }

    public Page<StudyDetailResponseDto> getStudies(StudySearchRequest condition, Pageable pageable) {
        return studyRepository.searchStudies(condition, pageable);
    }

    public Page<StudyCommentListResponseDto> getCommentList(String studyId, Pageable pageable) {
        validationAndGetStudy(studyId);

        return commentCustomRepository.findByComment(Long.valueOf(studyId), pageable);
    }

    public Study validationAndGetStudy(String studyId) {
        return studyRepository.findById(Long.parseLong(studyId)).orElseThrow(() -> {
            log.error("존재하지 않는 스터디 입니다. studyId = " + studyId);
            return new NoSuchElementException("존재하지 않는 스터디 입니다. ");
        });
    }

    public void comment(String studyId, StudyCommentReqeustDto reuqest) {
        Study study = validationAndGetStudy(studyId);
        CommentRepository.save(
                reuqest.toEntity(reuqest.getMemberId(),
                        Long.parseLong(studyId)));
    }
    @Transactional
    public void updateComment(StudyCommentUpdateRequestDto request) {
        Comment comment = CommentRepository.findById(request.getCommentId()).orElseThrow(() -> {
            log.error("존재하지 않는 댓글 입니다. studyId = " + request.getCommentId());
            return new NoSuchElementException("존재하지 않는 댓글 입니다. ");
        });

        comment.update(request);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = CommentRepository.findById(commentId).orElseThrow(() -> {
            log.error("존재하지 않는 댓글 입니다. studyId = " + commentId);
            return new NoSuchElementException("존재하지 않는 댓글 입니다. ");
        });

        CommentRepository.delete(comment);
    }

    @Transactional
    public void joinStudy(JoinStudyRequest joinStudyRequest) {
        Study study = studyRepository.findByIdWithPessimistic(joinStudyRequest.getStudyId()).orElseThrow(() -> {
            log.error("존재하지 않는 스터디 입니다. studyId = " + joinStudyRequest.getStudyId());
            return new NoSuchElementException("존재하지 않는 스터디 입니다. ");
        });

        if (study.getRemainingNumber() <= 0) {
            throw new IllegalStateException("참여 가능인원수가 "
                    + study.getRemainingNumber() + "명으로 참여할 수 없는 스터디입니다. ");
        }

        if (memberStudyRepository.existsByStudy_StudyIdAndMember_MemberId(
                joinStudyRequest.getStudyId(), joinStudyRequest.getMemberId())) {
            throw new IllegalStateException("이미 참여한 스터디 입니다.");
        }

        memberStudyRepository.save(joinStudyRequest.toEntity(ApproveStatus.WAIT));
    }

    public List<ChallengeDetailResponseDto> challengesByStudyId(String studyId) {
        List<Challenge> challenges = challengeRepository.findByStudy_StudyId(Long.parseLong(studyId));

        return challenges.stream().map(ChallengeDetailResponseDto::new).collect(Collectors.toList());
    }

    @Transactional
    public void endStudy(String studyId) {
        Study study = validationAndGetStudy(studyId);

        if (!challengesByStudyId(studyId).isEmpty())
            throw new IllegalStateException("챌린지가 존재하지 않아야 스터디 조기종료가 가능합니다.");

        study.endStudy();
    }

    @Transactional
    public void deleteStudy(String studyId, Long loginId) {
        Study study = validationAndGetStudy(studyId);

        if (loginId != (study.getMember().getMemberId()))
            throw new IllegalStateException("스터디 방장만 스터디를 삭제할 수 있습니다. ");

        List<MemberStudy> memberStudy = memberStudyRepository.findByStudy_StudyId(Long.parseLong(studyId));
        memberStudyRepository.deleteAll(memberStudy);
        studyRepository.delete(study);
    }

    public void approveJoinStudy(JoinStudyRequest approveJoinStudyRequest) {
        Study study = studyRepository.findByIdWithPessimistic(approveJoinStudyRequest.getStudyId()).orElseThrow(() -> {
            log.error("존재하지 않는 스터디 입니다. studyId = " + approveJoinStudyRequest.getStudyId());
            return new NoSuchElementException("존재하지 않는 스터디 입니다. ");
        });

        if (approveJoinStudyRequest.getMemberId() != (study.getMember().getMemberId()))
            throw new IllegalStateException("스터디 방장만 승인할 수 있습니다. ");

        if (study.getRemainingNumber() <= 0) {
            throw new IllegalStateException("참여 가능인원수가 "
                    + study.getRemainingNumber() + "명으로 참여할 수 없는 스터디입니다. ");
        }

        if (!memberStudyRepository.existsByStudy_StudyIdAndMember_MemberId(
                approveJoinStudyRequest.getStudyId(), approveJoinStudyRequest.getRequestMemberId())) {
            throw new IllegalStateException("참여한 적 없는 스터디입니다.");
        }

        MemberStudy memberStudy = memberStudyRepository.findMemberStudyByStudy_StudyIdAndMember_MemberId(approveJoinStudyRequest.getStudyId(),
                approveJoinStudyRequest.getRequestMemberId());

        study.decreaseRemainingNumber();
        memberStudy.approveJoin();
    }

    @Transactional
    public void denyJoinStudy(JoinStudyRequest denyJoinStudyRequest) {
        Study study = studyRepository.findByIdWithPessimistic(denyJoinStudyRequest.getStudyId()).orElseThrow(() -> {
            log.error("존재하지 않는 스터디 입니다. studyId = " + denyJoinStudyRequest.getStudyId());
            return new NoSuchElementException("존재하지 않는 스터디 입니다. ");
        });

        if (!memberStudyRepository.existsByStudy_StudyIdAndMember_MemberId(
                denyJoinStudyRequest.getStudyId(), denyJoinStudyRequest.getMemberId())) {
            throw new IllegalStateException("참여한 적 없는 스터디입니다.");
        }

        MemberStudy memberStudy = memberStudyRepository.findMemberStudyByStudy_StudyIdAndMember_MemberId(denyJoinStudyRequest.getStudyId(),
                denyJoinStudyRequest.getRequestMemberId());

        memberStudy.denyJoin();
    }
}
