package com.game.staticcontest.Static.Contest.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@Entity
public class ContestPlayArea {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String contestPlayAreaId;
    private String userId;

    @ManyToOne()
    @JoinColumn(name = "contest_id")
    private Contest contest;
    private String questionId;
    private Long startTime;
    private Long endTime;
    private String userAnswer;    //string of option id's seperated by comma
    private long skipped;
    private boolean attempted;
    private double score;
    private Integer questionSequence;


    public String getContestPlayAreaId() {
        return contestPlayAreaId;
    }

    public void setContestPlayAreaId(String contestPlayAreaId) {
        this.contestPlayAreaId = contestPlayAreaId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Contest getContest() {
        return contest;
    }

    public void setContest(Contest contest) {
        this.contest = contest;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getUserAnswer() {
        return userAnswer;
    }

    public void setUserAnswer(String userAnswer) {
        this.userAnswer = userAnswer;
    }

    public long getSkipped() {
        return skipped;
    }

    public void setSkipped(long skipped) {
        this.skipped = skipped;
    }

    public boolean isAttempted() {
        return attempted;
    }

    public void setAttempted(boolean attempted) {
        this.attempted = attempted;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public Integer getQuestionSequence() {
        return questionSequence;
    }

    public void setQuestionSequence(Integer questionSequence) {
        this.questionSequence = questionSequence;
    }

    @Override
    public String toString() {
        return "ContestPlayArea{" +
                "contestPlayAreaId='" + contestPlayAreaId + '\'' +
                ", userId='" + userId + '\'' +
                ", contest=" + contest +
                ", questionId='" + questionId + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", userAnswer='" + userAnswer + '\'' +
                ", skipped=" + skipped +
                ", attempted=" + attempted +
                ", score=" + score +
                ", questionSequence='" + questionSequence + '\'' +
                '}';
    }
}
