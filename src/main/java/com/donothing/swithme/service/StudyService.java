package com.donothing.swithme.service;

import com.donothing.swithme.domain.Comment;
import com.donothing.swithme.domain.Member;
import com.donothing.swithme.domain.MemberStudy;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.study.*;
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

    private final CommentRepository commentRepository;

    private final MemberStudyRepository memberStudyRepository;

    @Transactional
    public StudyRegisterResponseDto registerStudy(StudyRegisterRequestDto request) {
        Study study = studyRepository.save(request.toEntity());

        memberStudyRepository.save(MemberStudy.builder()
                        .study(study)
                        .member(new Member(request.getMemberId()))
                .build());

        return new StudyRegisterResponseDto(study.getStudyId());
    }

    public StudyDetailResponseDto detailStudyByStudyId(String studyId) {
        Study study = validationAndGetStudy(studyId);
        return new StudyDetailResponseDto(study);
    }
    @Transactional
    public void updateStudy(String studyId, StudyUpdateRequestDto reuqest) {
        Study study = validationAndGetStudy(studyId);
        study.update(reuqest);
    }

    public Page<StudyDetailResponseDto> getStudies(StudySearchRequest condition, Pageable pageable) {
        return studyRepository.searchStudies(condition, pageable);
    }

    public List<StudyCommentListResponseDto> getCommentList(String studyId) {
        validationAndGetStudy(studyId);
        List<Comment> allComments = commentRepository.findByStudy_StudyId(Long.valueOf(studyId));

        // 대댓글이 아닌 댓글들을 추출하여 StudyCommentListResponseDto로 매핑
        List<StudyCommentListResponseDto> comments = allComments.stream()
                .filter(c -> c.getCommentTag() == null) // 대댓글이 아닌 댓글 필터링
                .map(StudyCommentListResponseDto::new)
                .collect(Collectors.toList());

        // 각 댓글에 대해 대댓글 추가
        for (StudyCommentListResponseDto comment : comments) {
            List<StudyCommentListResponseDto> recomment = allComments.stream()
                    .filter(c -> c.getCommentTag() != null && c.getCommentTag().equals(comment.getCommentId()))
                    .map(StudyCommentListResponseDto::new)
                    .collect(Collectors.toList());

            comment.setRecomment(recomment);
        }

        return comments;
    }

    public Study validationAndGetStudy(String studyId) {
        return studyRepository.findById(Long.parseLong(studyId)).orElseThrow(() -> {
            log.error("존재하지 않는 스터디 입니다. studyId = " + studyId);
            return new NoSuchElementException("존재하지 않는 스터디 입니다. ");
        });
    }

    public void comment(String studyId, StudyCommentReqeustDto reuqest) {
        Study study = validationAndGetStudy(studyId);
        commentRepository.save(
                reuqest.toEntity(reuqest.getMemberId(),
                        Long.parseLong(studyId)));
    }
    @Transactional
    public void updateComment(StudyCommentUpdateRequestDto request) {
        Comment comment = commentRepository.findById(request.getCommentId()).orElseThrow(() -> {
            log.error("존재하지 않는 댓글 입니다. studyId = " + request.getCommentId());
            return new NoSuchElementException("존재하지 않는 댓글 입니다. ");
        });

        comment.update(request);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> {
            log.error("존재하지 않는 댓글 입니다. studyId = " + commentId);
            return new NoSuchElementException("존재하지 않는 댓글 입니다. ");
        });

        commentRepository.delete(comment);
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

        study.decreaseRemainingNumber();
        memberStudyRepository.save(joinStudyRequest.toEntity());
    }
}
