package com.ippt;

public class TvDbException extends Exception {
    public TvDbException(String message) {
        super(message);
    }

    public TvDbException(String message, Throwable cause) {
        super(message, cause);
    }

    public TvDbException(Throwable cause) {
        super(cause);
    }

}
