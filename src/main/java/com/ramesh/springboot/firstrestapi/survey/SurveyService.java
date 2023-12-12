package com.ramesh.springboot.firstrestapi.survey;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Service
public class SurveyService {

    private static List<Survey> surveys = new ArrayList<>();

    static {
        Question question1 = new Question("Question1",
                "Most Popular Cloud Platform Today", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "AWS");
        Question question2 = new Question("Question2",
                "Fastest Growing Cloud Platform", Arrays.asList(
                "AWS", "Azure", "Google Cloud", "Oracle Cloud"), "Google Cloud");
        Question question3 = new Question("Question3",
                "Most Popular DevOps Tool", Arrays.asList("Kubernetes", "Docker", "Terraform", "Azure DevOps"),
                "Kubernetes");

        List<Question> questions = new ArrayList<>(Arrays.asList(question1, question2, question3));

        Survey survey = new Survey("Survey1", "My Favorite Survey",
                "Description of the Survey", questions);

        surveys.add(survey);

    }

    public List<Survey> retrieveAllSurvey() {
        return surveys;
    }

    public Survey retrieveSurveyById(String surveyId) {
        return surveys.stream().filter(survey -> survey.getId().equalsIgnoreCase(surveyId)).findFirst().orElse(null);
    }

    public List<Question> retrieveAllSurveyQuestions(String surveyId) {
        Survey survey = retrieveSurveyById(surveyId);
        if (null == survey) return null;

        return survey.getQuestions();
    }

    public Question retrieveSurveyQuestionByQuestionId(String surveyId, String questionId) {
        List<Question> questionList = retrieveAllSurveyQuestions(surveyId);
        if (questionList.isEmpty()) return null;

        return questionList.stream().filter(question -> question.getId().equalsIgnoreCase(questionId)).findFirst().orElse(null);
    }

    public String addNewSurveyQuestion(String surveyId, Question question) {

        // first logic
       /* Survey survey = retrieveSurveyById(surveyId);
        if (null == survey) return false;
        surveys.remove(survey);
        System.out.println("survey size is : " +surveys.size());

        List<Question> questionList = survey.getQuestions();
        questionList.add(question);

        survey.setQuestions(questionList);
        surveys.add(survey);
        return true;*/

        // second logic
        List<Question> questions = retrieveAllSurveyQuestions(surveyId);
        if (questions.isEmpty()) return null;

        String randomNumber = randomNumberGenerator();
        question.setId(randomNumber);
        questions.add(question);
        return randomNumber;
    }

    private String randomNumberGenerator() {
        SecureRandom secureRandom = new SecureRandom();
        return new BigInteger(32, secureRandom).toString();
    }

    public boolean deleteSurveyQuestionByQuestionId(String surveyId, String questionId) {
        List<Question> questionList = retrieveAllSurveyQuestions(surveyId);
        if (questionList.isEmpty()) return false;

        Predicate<Question> predicate = question -> question.getId().equalsIgnoreCase(questionId);
        return questionList.removeIf(predicate);
    }

    public String updateSurveyQuestionByQuestionId(String surveyId, String questionId, Question question) {
        List<Question> questionList = retrieveAllSurveyQuestions(surveyId);
        questionList.removeIf(q -> q.getId().equalsIgnoreCase(questionId));
        questionList.add(question);

        return questionId;

    }
}
