package com.game.staticcontest.Static.Contest.dto;

public class SubmitSubscriptionDTO {

    private String contestId;
    private String contestName;
    private String userId;

    public String getContestId() {
        return contestId;
    }

    public void setContestId(String contestId) {
        this.contestId = contestId;
    }

    public String getContestName() {
        return contestName;
    }

    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "SubmitSubscriptionDTO{" +
                "contestId='" + contestId + '\'' +
                ", contestName='" + contestName + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
