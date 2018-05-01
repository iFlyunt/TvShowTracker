package com.ippt.api.auth;

import com.ippt.TvDbException;

public interface TvDbAuthentication {

    String authenticate(String credentials)
            throws TvDbException, TvDbAuthenticationException;

    String reAuthenticate() throws TvDbException, TvDbAuthenticationException;
}
