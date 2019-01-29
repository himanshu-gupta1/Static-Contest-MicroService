package com.game.staticcontest.Static.Contest.dto;

public class ResponseDTO<T> {
    private String status;
    private String errorMessage;
    private T response;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponseDTO{" +
                "status='" + status + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", response=" + response +
                '}';
    }
}