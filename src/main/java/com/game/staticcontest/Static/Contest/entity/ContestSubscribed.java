package com.game.staticcontest.Static.Contest.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ContestSubscribed {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String ContestsubscribedId;

    private String userId;

    @ManyToOne()
    @JoinColumn(name = "contest_id")
    private Contest contest;

    private boolean finished;

    private double score;

    public String getContestsubscribedId() {
        return ContestsubscribedId;
    }

    public void setContestsubscribedId(String contestsubscribedId) {
        ContestsubscribedId = contestsubscribedId;
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

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }


    @Override
    public String toString() {
        return "ContestSubscribed{" +
                "ContestsubscribedId='" + ContestsubscribedId + '\'' +
                ", userId='" + userId + '\'' +
                ", contest=" + contest +
                ", finished=" + finished +
                ", score=" + score +
                '}';
    }
}
