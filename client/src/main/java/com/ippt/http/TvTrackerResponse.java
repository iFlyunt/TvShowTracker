package com.ippt.http;

public class TvTrackerResponse {
    private String body;
    private int statusCode;

    TvTrackerResponse(String body, int statusCode) {
        this.body = body;
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
