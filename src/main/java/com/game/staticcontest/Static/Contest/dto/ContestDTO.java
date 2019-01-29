package com.game.staticcontest.Static.Contest.dto;

public class ContestDTO {

    private String contestId;

    private String name;
    private String type;
    private int skips;
    private int noOfQuestions;
    private String categoryId;
    private boolean active;
    private String difficulty;
    private boolean isSubscribed;

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSkips() {
        return skips;
    }

    public void setSkips(int skips) {
        this.skips = skips;
    }

    public int getNoOfQuestions() {
        return noOfQuestions;
    }

    public void setNoOfQuestions(int noOfQuestions) {
        this.noOfQuestions = noOfQuestions;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }


    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }


    @Override
    public String toString() {
        return "ContestDTO{" +
                "contestId='" + contestId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", skips=" + skips +
                ", noOfQuestions=" + noOfQuestions +
                ", categoryId='" + categoryId + '\'' +
                ", active=" + active +
                ", difficulty='" + difficulty + '\'' +
                ", isSubscribed=" + isSubscribed +
                '}';
    }
}
