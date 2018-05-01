package com.tvshowtracker.api.auth;

import com.tvshowtracker.TvDbException;

public interface TvDbAuthentication {

    String authenticate(String credentials)
            throws TvDbException, TvDbAuthenticationException;

    String reAuthenticate() throws TvDbException, TvDbAuthenticationException;
}
