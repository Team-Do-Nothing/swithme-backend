package com.donothing.swithme;

import com.donothing.swithme.domain.Study;
import com.donothing.swithme.dto.study.JoinStudyRequest;
import com.donothing.swithme.repository.MemberStudyRepository;
import com.donothing.swithme.repository.StudyRepository;
import com.donothing.swithme.service.StudyService;
import jdk.jshell.spi.ExecutionControl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommentServiceTest {

    @Autowired
    private StudyService studyService;

    @Autowired
    private StudyRepository studyRepository;

    @Test
    public void 댓글달기() {
        // given
        JoinStudyRequest request = JoinStudyRequest.builder()
                .studyId(1L)
                .memberId(1L)
                .build();

        // when
        studyService.joinStudy(request);
        Study study = studyRepository.findById(1L).orElseThrow();

        // then
        Assert.assertEquals(9, study.getRemainingNumber());
    }
}
