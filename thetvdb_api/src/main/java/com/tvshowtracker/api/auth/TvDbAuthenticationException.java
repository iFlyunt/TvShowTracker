package com.tvshowtracker.api.auth;

public class TvDbAuthenticationException extends Exception {
    public TvDbAuthenticationException() {}

    public TvDbAuthenticationException(String message) {
        super(message);
    }
}
