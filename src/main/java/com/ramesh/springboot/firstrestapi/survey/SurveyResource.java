package com.ramesh.springboot.firstrestapi.survey;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController()
public class SurveyResource {

    private SurveyService surveyService;

    public SurveyResource(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @RequestMapping(value = "/surveys", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Survey> retrieveAllSurvey() {
        return surveyService.retrieveAllSurvey();
    }

    @RequestMapping("/surveys/{surveyId}")
    public Survey retrieveSurveyById(@PathVariable String surveyId) {
        Survey survey = surveyService.retrieveSurveyById(surveyId);
        if (null == survey) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return survey;
    }

    @RequestMapping("/surveys/{surveyId}/questions")
    public List<Question> retrieveAllSurveyQuestions(@PathVariable String surveyId) {
        List<Question> questionList = surveyService.retrieveAllSurveyQuestions(surveyId);
        if (questionList.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return questionList;

    }

    @RequestMapping(value = "/surveys/{surveyId}/questions", method = RequestMethod.POST)
    public ResponseEntity<Object> addNewSurveyQuestion(@PathVariable String surveyId, @RequestBody Question question) {
        String questionId = surveyService.addNewSurveyQuestion(surveyId, question);
        if (null == questionId) {
            return ResponseEntity.created(null).body("Question added failed");
        } else {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{questionId}")
                    .buildAndExpand(questionId)
                    .toUri();
            return ResponseEntity.created(location).body("Question added successfully");
        }
    }

    @RequestMapping("/surveys/{surveyId}/questions/{questionId}")
    public Question retrieveSurveyQuestionByQuestionId(@PathVariable String surveyId, @PathVariable String questionId) {
        Question question = surveyService.retrieveSurveyQuestionByQuestionId(surveyId, questionId);
        if (null == question) throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return question;

    }

    @RequestMapping(value = "/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteSurveyQuestionByQuestionId(@PathVariable String surveyId, @PathVariable String questionId) {
        boolean response = surveyService.deleteSurveyQuestionByQuestionId(surveyId, questionId);

        if(response) {
            return ResponseEntity.ok("Question deleted successfully");
        } else {
            return ResponseEntity.ok("Question deleted failed");
        }

    }

    @RequestMapping(value = "/surveys/{surveyId}/questions/{questionId}", method = RequestMethod.PUT)
    public ResponseEntity<Object> updateSurveyQuestion(@PathVariable String surveyId, @PathVariable String questionId,
                                                       @RequestBody Question question) {
        String response = surveyService.updateSurveyQuestionByQuestionId(surveyId, questionId, question);

        return ResponseEntity.ok("Question updated successfully");

    }
}
