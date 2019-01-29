package com.game.staticcontest.Static.Contest.dto;

import java.util.List;

public class RequestListDTO<T> {

    private List<T> requestList;
    private String userId;

    public List<T> getRequestList() {
        return requestList;
    }

    public void setRequestList(List<T> requestList) {
        this.requestList = requestList;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }


    @Override
    public String toString() {
        return "RequestListDTO{" +
                "requestList=" + requestList +
                ", userId='" + userId + '\'' +
                '}';
    }
}
