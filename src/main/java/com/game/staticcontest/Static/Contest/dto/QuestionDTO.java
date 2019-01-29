package com.game.staticcontest.Static.Contest.dto;

public class QuestionDTO {

    private String questionId;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    @Override
    public String toString() {
        return "QuestionDTO{" +
                "questionId='" + questionId + '\'' +
                '}';
    }
}
