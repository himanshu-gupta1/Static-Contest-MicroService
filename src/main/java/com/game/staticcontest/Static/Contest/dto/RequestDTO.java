package com.game.staticcontest.Static.Contest.dto;

public class RequestDTO<T> {
    private T request;
    private String userId;

    public T getRequest() {
        return request;
    }

    public void setRequest(T request) {
        this.request = request;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "RequestDTO{" +
                "request=" + request +
                ", userId='" + userId + '\'' +
                '}';
    }
}
