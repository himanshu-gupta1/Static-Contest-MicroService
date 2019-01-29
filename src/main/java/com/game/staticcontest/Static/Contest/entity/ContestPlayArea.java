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
    private String questionNo;




}
