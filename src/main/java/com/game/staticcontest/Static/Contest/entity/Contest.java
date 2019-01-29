package com.game.staticcontest.Static.Contest.entity;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Contest {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name="uuid",strategy = "uuid2")
    private String contestId;

    private String name;
    private String type;
    private int skips;
    private int noOfQuestions;
    private String categoryId;
    private boolean active;
    private String difficulty;

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


    @Override
    public String toString() {
        return "ContestDetail{" +
                "contestId='" + contestId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", skips=" + skips +
                ", noOfQuestions=" + noOfQuestions +
                ", categoryId='" + categoryId + '\'' +
                ", active=" + active +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }
}
