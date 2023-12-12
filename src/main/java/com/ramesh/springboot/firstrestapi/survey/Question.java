package com.ramesh.springboot.firstrestapi.survey;

import java.util.List;

public class Question {

    private String id;
    private String description;
    private List<String> option;
    private String correctAnswer;

    public Question() {
    }

    public Question(String id, String description, List<String> option, String correctAnswer) {
        this.id = id;
        this.description = description;
        this.option = option;
        this.correctAnswer = correctAnswer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getOption() {
        return option;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", option=" + option +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}
