package com.game.staticcontest.Static.Contest.dto;

public class ContentDTO {

    private String text;
    private String url;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ContentDTO{" +
                "text='" + text + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
