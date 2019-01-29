package com.game.staticcontest.Static.Contest.dto;

public class ResponseDTO<T> {
    private String status;
    private String errorMessage;
    private T response;
}