package com.game.staticcontest.Static.Contest.entity;

import javax.persistence.*;

@Entity
public class ContestQuestion {

    @Id
    private String contestQuestionId;


    @ManyToOne()
    @JoinColumn(name = "contest_id")
    private Contest contest;

    private String questionId;

    private String questionNo;





}
