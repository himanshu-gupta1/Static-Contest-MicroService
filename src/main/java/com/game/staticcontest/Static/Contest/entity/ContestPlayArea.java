package com.game.staticcontest.Static.Contest.entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class ContestPlayArea {

    @Id
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
    private String questionNo;




}
