package com.game.staticcontest.Static.Contest.dto;

import java.util.List;

public class ContestPlayRequestDTO {

    private String questionId;
    private String questionSequence;
    private List<OptionDTO> userAnswerList;

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

    public List<OptionDTO> getUserAnswerList() {
        return userAnswerList;
    }

    public void setUserAnswerList(List<OptionDTO> userAnswerList) {
        this.userAnswerList = userAnswerList;
    }


    @Override
    public String toString() {
        return "ContestPlayRequestDTO{" +
                "questionId='" + questionId + '\'' +
                ", questionSequence='" + questionSequence + '\'' +
                ", userAnswerList=" + userAnswerList +
                '}';
    }
}
