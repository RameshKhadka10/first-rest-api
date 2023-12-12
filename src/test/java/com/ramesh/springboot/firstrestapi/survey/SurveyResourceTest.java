package com.ramesh.springboot.firstrestapi.survey;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(value = SurveyResource.class)
@AutoConfigureMockMvc(addFilters = false)
public class SurveyResourceTest {

    @MockBean
    private SurveyService surveyService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void retrieveSurveyQuestionByQuestionIdInValidTest() throws Exception {
        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/surveys/survey1/questions/Question2"));
        mvcResult.andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    void retrieveSurveyQuestionByQuestionIdValidTest() throws Exception {
        Question mockQuestion = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");

        String expectedResponse = """
                {
                  "id": "Question1",
                  "description": "Most Popular Cloud Platform Today",
                  "option": [
                    "AWS",
                    "Azure",
                    "Google Cloud",
                    "Oracle Cloud"
                  ],
                  "correctAnswer": "AWS"
                }
                """;

        when(surveyService.retrieveSurveyQuestionByQuestionId("survey1", "Question2")).thenReturn(mockQuestion);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/surveys/survey1/questions/Question2")).andReturn();

        assertEquals(200, mvcResult.getResponse().getStatus());
        JSONAssert.assertEquals(expectedResponse, mvcResult.getResponse().getContentAsString(), true);
    }

    @Test
    void addNewSurveyQuestionValidTest() throws Exception {
        String requestBody = """
                {
                  "description": "Most Popular Cloud Platform Today",
                  "option": [
                    "AWS",
                    "Azure",
                    "Google Cloud",
                    "Oracle Cloud"
                  ],
                  "correctAnswer": "AWS"
                }
                """;

        when(surveyService.addNewSurveyQuestion(anyString(), any())).thenReturn("123");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/surveys/survey1/questions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        assertEquals(201, response.getStatus());
        assertEquals("Question added successfully", response.getContentAsString());
    }
}
