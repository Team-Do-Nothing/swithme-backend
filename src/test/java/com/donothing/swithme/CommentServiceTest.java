package com.donothing.swithme;

import com.donothing.swithme.domain.Comment;
import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.study.JoinStudyRequest;
import com.donothing.swithme.dto.study.StudyCommentReqeustDto;
import com.donothing.swithme.repository.CommentRepository;
import com.donothing.swithme.service.StudyService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentServiceTest {
    @Autowired
    private StudyService studyService;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void 댓글달기() {
        // given
        String studyId = "1";
        StudyCommentReqeustDto request = StudyCommentReqeustDto.builder()
                .recommentId(null)
                .comment("댓글 달았지롱")
                .memberId(1L)
                .build();

        // when
        studyService.comment(studyId, request);
        Comment comment = commentRepository.findById(1L).orElseThrow();

        // then
        Assert.assertEquals("댓글 달았지롱", comment.getComment());
    }

    @Test
    public void 대댓글달기() {
        // given
        String studyId = "1";
        StudyCommentReqeustDto request = StudyCommentReqeustDto.builder()
                .recommentId(1L)
                .comment("대댓글 달았지롱")
                .memberId(1L)
                .build();

        // when
        studyService.comment(studyId, request);
        Comment comment = commentRepository.findById(2L).orElseThrow();

        // then
        Assert.assertEquals("대댓글 달았지롱", comment.getComment());
    }
}
