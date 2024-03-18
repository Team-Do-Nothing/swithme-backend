package com.donothing.swithme.service;

import com.donothing.swithme.common.PagingData;
import com.donothing.swithme.domain.Comment;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.study.*;
import com.donothing.swithme.repository.CommentRepository;
import com.donothing.swithme.repository.StudyRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;

    private final CommentRepository commentRepository;

    @Transactional
    public StudyRegisterResponseDto registerStudy(StudyRegisterRequestDto request) {
        Study study = studyRepository.save(request.toEntity());
        return new StudyRegisterResponseDto(study.getStudyId());
    }

    public StudyDetailResponseDto detailStudyByStudyId(String studyId) {
        Study study = validationAndGetStudy(studyId);
        return new StudyDetailResponseDto(study);
    }

    public StudyDetailResponseDto updateStudy(String studyId, StudyUpdateReqeustDto reuqest) {
        Study study = validationAndGetStudy(studyId);
        study.update(reuqest);

        return null;
    }

    public Page<Study> getStudies(StudySearchRequest condition, Pageable pageable) {
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
            List<StudyCommentListResponseDto> recommnet = allComments.stream()
                    .filter(c -> c.getCommentTag() != null && c.getCommentTag().equals(comment.getCommentId()))
                    .map(StudyCommentListResponseDto::new)
                    .collect(Collectors.toList());

            comment.setRecommnet(recommnet);
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
                reuqest.toEntity(Long.parseLong(studyId),
                        Long.parseLong(studyId)));
    }
}
