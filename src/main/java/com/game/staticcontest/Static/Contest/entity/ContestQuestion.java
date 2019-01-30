package com.game.staticcontest.Static.Contest.entity;

import org.hibernate.annotations.GeneratorType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class ContestQuestion {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String contestQuestionId;


    @ManyToOne()
    @JoinColumn(name = "contest_id")
    private Contest contest;

    private String questionId;

    private Integer questionSequence;

    public String getContestQuestionId() {
        return contestQuestionId;
    }

    public void setContestQuestionId(String contestQuestionId) {
        this.contestQuestionId = contestQuestionId;
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

    public Integer getQuestionSequence() {
        return questionSequence;
    }

    public void setQuestionSequence(Integer questionSequence) {
        this.questionSequence = questionSequence;
    }

    @Override
    public String toString() {
        return "ContestQuestion{" +
                "contestQuestionId='" + contestQuestionId + '\'' +
                ", contest=" + contest +
                ", questionId='" + questionId + '\'' +
                ", questionSequence='" + questionSequence + '\'' +
                '}';
    }
}
