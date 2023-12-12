package com.ramesh.springboot.firstrestapi.survey;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SurveyResourceIT {

    @Autowired
    TestRestTemplate template;

    @Test
    void addNewSurveyQuestionTest() {

       /*
       Question question = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");

        ResponseEntity<String> response = template.postForEntity("/surveys/survey1/questions", question, String.class);
        */

        // sending content type and request body
        String requestBody = """
                {
                  "description": "Fastest Growing programming language",
                  "option": [
                    "Java",
                    "JavaScript",
                    "Python",
                    "Scala"
                  ],
                  "correctAnswer": "Java"
                }
                """;

        HttpHeaders httpHeaders = createHttpContentTypeAndAuthorizationHeader();

        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody, httpHeaders);
        ResponseEntity<String> response = template.exchange("/surveys/survey1/questions", HttpMethod.POST, httpEntity, String.class);
        assertEquals("Question added successfully", response.getBody());

        URI locationHeader = response.getHeaders().getLocation();
        ResponseEntity<String> deleteResponse = template.exchange(locationHeader, HttpMethod.DELETE, httpEntity, String.class);
        assertTrue(deleteResponse.getStatusCode().is2xxSuccessful());
    }

    private HttpHeaders createHttpContentTypeAndAuthorizationHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Authorization", "Basic " + performBasicAuthEncoding("ramesh", "ramesh123"));

        return httpHeaders;
    }

    @Test
    void retrieveSurveyQuestionByQuestionId_test() throws JSONException {
        HttpHeaders httpHeaders = createHttpContentTypeAndAuthorizationHeader();
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<String> response = template.exchange("/surveys/survey1/questions/Question2", HttpMethod.GET, httpEntity, String.class);

        //  ResponseEntity<String> response = template.getForEntity("/surveys/survey1/questions/Question2", String.class);

        String expectedResponse = """
                {
                  "id": "Question2",
                  "description": "Fastest Growing Cloud Platform",
                  "option": [
                    "AWS",
                    "Azure",
                    "Google Cloud",
                    "Oracle Cloud"
                  ],
                  "correctAnswer": "Google Cloud"
                }
                """;

        JSONAssert.assertEquals(expectedResponse, response.getBody(), true);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(response.getHeaders().getContentType().toString(), "application/json");
    }

    @Test
    void retrieveAllSurveyQuestionsTest() throws JSONException {
        HttpHeaders httpHeaders = createHttpContentTypeAndAuthorizationHeader();
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);

        ResponseEntity<String> response = template.exchange("/surveys/survey1/questions", HttpMethod.GET, httpEntity, String.class);

        //ResponseEntity<String> response = template.getForEntity("/surveys/survey1/questions", String.class);

        String expectedResponse = """
                [
                  {
                    "id": "Question1"
                  },
                  {
                    "id": "Question2"
                  },
                  {
                    "id": "Question3"
                  }
                ]
                """;
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals("application/json", response.getHeaders().getContentType().toString());
        JSONAssert.assertEquals(expectedResponse, response.getBody(), false);
    }

    private String performBasicAuthEncoding(String user, String password) {
        String combined = user + ":" + password;
        byte[] bytes = Base64.getEncoder().encode(combined.getBytes());

        return new String(bytes);
    }

}
