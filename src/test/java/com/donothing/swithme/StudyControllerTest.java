package com.donothing.swithme;

import com.donothing.swithme.domain.Study;
import com.donothing.swithme.domain.StudyType;
import com.donothing.swithme.dto.study.JoinStudyRequest;
import com.donothing.swithme.dto.study.StudyRegisterRequestDto;
import com.donothing.swithme.repository.MemberStudyRepository;
import com.donothing.swithme.repository.StudyRepository;
import com.donothing.swithme.service.StudyService;
import jdk.jshell.spi.ExecutionControl;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
@RunWith(SpringRunner.class)
@TestPropertySource(properties = {
        "cloud.aws.credentials.access-key=test-access-key",
        "cloud.aws.credentials.secret-key=test-secret-key"
})
public class StudyControllerTest {

    @Autowired
    private StudyService studyService;

    @Autowired
    private StudyRepository studyRepository;

    @Autowired
    private MemberStudyRepository memberStudyRepository;

    @Test
    public void 동시에_스터디_참여_요청() throws InterruptedException {
        // given
        int threadCnt = 9;

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCnt);
        for (int i = 0; i < threadCnt; i++) {
            JoinStudyRequest request = JoinStudyRequest.builder()
                    .studyId(1L)
                    .memberId(2L + i)
                    .build();

            executorService.submit(() -> {
                try {
                    studyService.joinStudy(request);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Study study = studyRepository.findById(1L).orElseThrow();
        Assert.assertEquals(0, study.getRemainingNumber());
    }

    @Test
    public void 스터디_참여() {
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

    @Test
    public void 이미_스터디에_참여한사람은_불가능하다() {
        // given
        JoinStudyRequest request = JoinStudyRequest.builder()
                .studyId(1L)
                .memberId(1L)
                .build();

        // then
        Assert.assertThrows(IllegalStateException.class, () -> {
            studyService.joinStudy(request);
        });
    }

    @Test
    public void 스터디에_참여하지않은사람은_true() {
        // given
        Long studyId = 1L;
        Long memberId = 1L;

        // when
        boolean result = memberStudyRepository.existsByStudy_StudyIdAndMember_MemberId(studyId, memberId);

        // then
        Assert.assertTrue(result);
    }

    @Test
    public void 스터디에_참여하지않은사람은_false() {
        // given
        Long studyId = 1L;
        Long memberId = 2L;

        // when
        boolean result = memberStudyRepository.existsByStudy_StudyIdAndMember_MemberId(studyId, memberId);

        // then
        Assert.assertFalse(result);
    }
//
//    @Test
//    public void 스터디_생성() {
//        // given
//        StudyRegisterRequestDto given = StudyRegisterRequestDto.builder()
//                .title("자바 스터디 모집합니다. ")
//                .categoryId(1L)
//                .dateStudyStart("2024-07-01")
//                .dateStudyEnd("2024-07-31")
//                .regionCode("1")
//                .studyInfo("취준생들 환영합니다 자바 기초부터 같이 공부해요!")
//                .studyType(StudyType.OFFLINE)
//                .numberOfMembers(10)
//                .memberId(1L)
//                .build();
//
//        // when
//        studyService.registerStudy(given);
//
//        // then
//        Assert.assertEquals("자바 스터디 모집합니다. ", studyRepository.findById(1L).get().getTitle());
//    }
}
