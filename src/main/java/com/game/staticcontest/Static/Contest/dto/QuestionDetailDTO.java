package com.game.staticcontest.Static.Contest.dto;

import java.util.List;

public class QuestionDetailDTO {

    private String questionId;
    private String questionName;
    private String questionContent;
    private List<OptionDTO> optionDTOList;
   // private List<OptionDTO> correctDTOList;
    private String questionCategory;
    private String questionType;
    private String answerType;
    private String questionDifficulty;
    private int duration;


    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }



    public List<OptionDTO> getOptionDTOList() {
        return optionDTOList;
    }

    public void setOptionDTOList(List<OptionDTO> optionDTOList) {
        this.optionDTOList = optionDTOList;
    }



    public String getQuestionCategory() {
        return questionCategory;
    }

    public void setQuestionCategory(String questionCategory) {
        this.questionCategory = questionCategory;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getAnswerType() {
        return answerType;
    }

    public void setAnswerType(String answerType) {
        this.answerType = answerType;
    }

    public String getQuestionDifficulty() {
        return questionDifficulty;
    }

    public void setQuestionDifficulty(String questionDifficulty) {
        this.questionDifficulty = questionDifficulty;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }


    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }


    @Override
    public String toString() {
        return "QuestionDetailDTO{" +
                "questionId='" + questionId + '\'' +
                ", questionName='" + questionName + '\'' +
                ", questionContent='" + questionContent + '\'' +
                ", optionDTOList=" + optionDTOList +
                ", questionCategory='" + questionCategory + '\'' +
                ", questionType='" + questionType + '\'' +
                ", answerType='" + answerType + '\'' +
                ", questionDifficulty='" + questionDifficulty + '\'' +
                ", duration=" + duration +
                '}';
    }
}
