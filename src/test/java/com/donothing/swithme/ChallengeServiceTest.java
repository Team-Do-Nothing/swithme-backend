package com.donothing.swithme;

import com.donothing.swithme.domain.ChallengeStatus;
import com.donothing.swithme.dto.challenge.ChallengeRegisterRequestDto;
import com.donothing.swithme.dto.challenge.ChallengeRegisterResponseDto;
import com.donothing.swithme.service.ChallengeService;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ChallengeServiceTest {
    @Autowired
    private ChallengeService challengeService;

    @Test
    public void 챌린지_생성하기() {
        // given
        ChallengeRegisterRequestDto request = ChallengeRegisterRequestDto.builder()
                .memberId(1L)
                .studyId(1L)
                .title("자격증 같이 따요~")
                .goal("자격증 따기")
                .startDate("20240528")
                .endDate("20241231")
                .challengeFee(1000)
                .checkFormat("오늘 하루 공부한 거 사진 찍기")
                .challengeStatus(ChallengeStatus.DAILY)
                .build();

        // when
        ChallengeRegisterResponseDto response = challengeService.registChallenge(request);

        // then
        Assert.assertEquals(Optional.of(1L), response.getChallengeId());
    }
}
