package com.donothing.study;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.donothing.swithme.SwithmeApplication;
import com.donothing.swithme.domain.StudyStatus;
import com.donothing.swithme.domain.StudyType;
import com.donothing.swithme.dto.study.StudyUpdateReqeustDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@TestPropertySource(locations = "classpath:application-test.properties")  // Specify the test properties file
@SpringBootTest(classes = SwithmeApplication.class)
@AutoConfigureMockMvc
public class StudyTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void updateTest() throws Exception {
        // given
        StudyUpdateReqeustDto request =
                new StudyUpdateReqeustDto("수정TEST",
                        StudyType.ONLINE,
                        3,
                        "study info 수정",
                        StudyStatus.CURR,
                        "20231127",
                        "20231231");
        // when
        mockMvc.perform(put("/api/v1/study/3")
                        .content(asJsonString(request))
                        .contentType("application/json"))
                .andExpect(status().isOk());

    }

    private String asJsonString(Object obj) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }


}
