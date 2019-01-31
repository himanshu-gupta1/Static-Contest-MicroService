package com.game.staticcontest.Static.Contest.dto;

public class SubmitContestDTO {

    private String contestId;
    private String userId;
    private String contestName;
    private double score;

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "SubmitContestDTO{" +
                "contestId='" + contestId + '\'' +
                ", userId='" + userId + '\'' +
                ", contestName='" + contestName + '\'' +
                ", score=" + score +
                '}';
    }
}
