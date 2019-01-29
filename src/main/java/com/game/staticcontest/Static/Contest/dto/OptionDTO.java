package com.game.staticcontest.Static.Contest.dto;

public class OptionDTO {

    private String questionId;
    private String optionId;
    private String optionContent;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getOptionId() {
        return optionId;
    }

    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }

    public String getOptionContent() {
        return optionContent;
    }

    public void setOptionContent(String optionContent) {
        this.optionContent = optionContent;
    }

    @Override
    public String toString() {
        return "OptionDTO{" +
                "optionId='" + optionId + '\'' +
                ", optionContent='" + optionContent + '\'' +
                '}';
    }
}
