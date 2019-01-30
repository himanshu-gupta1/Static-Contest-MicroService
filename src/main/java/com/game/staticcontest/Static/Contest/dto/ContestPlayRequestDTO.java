package com.game.staticcontest.Static.Contest.dto;

import java.util.List;

public class ContestPlayRequestDTO {

    private String questionId;
    private String questionSequence;
    private String optionIds;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionSequence() {
        return questionSequence;
    }

    public void setQuestionSequence(String questionSequence) {
        this.questionSequence = questionSequence;
    }


    public String getOptionIds() {
        return optionIds;
    }

    public void setOptionIds(String optionIds) {
        this.optionIds = optionIds;
    }

    @Override
    public String toString() {
        return "ContestPlayRequestDTO{" +
                "questionId='" + questionId + '\'' +
                ", questionSequence='" + questionSequence + '\'' +
                ", optionIds='" + optionIds + '\'' +
                '}';
    }
}
