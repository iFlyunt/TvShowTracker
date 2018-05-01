package com.tvshowtracker.api.auth;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tvshowtracker.TvDbException;
import com.tvshowtracker.TvDbUriResourceBuilder;
import com.tvshowtracker.TvDbUriResourceConstants;
import com.tvshowtracker.http.TvDbHttpRequest;
import com.tvshowtracker.http.TvDbHttpRequestImpl;
import org.apache.http.Header;
import org.apache.http.HttpHeaders;
import org.apache.http.message.BasicHeader;

import java.net.URI;

public class TvDbAuthenticationImpl implements TvDbAuthentication {
    private String          token;
    private TvDbHttpRequest tvDbHttpRequest;
    private URI             uri;
    private Header          header;

    public TvDbAuthenticationImpl() {
        tvDbHttpRequest = new TvDbHttpRequestImpl();
    }

    @Override
    public String authenticate(String credentials)
            throws TvDbException, TvDbAuthenticationException {
        setupHttpParamsForAuthentication();
        String json = tvDbHttpRequest.post(uri, credentials, header);
        return this.createToken(json);
    }

    @Override
    public String reAuthenticate() throws TvDbException, TvDbAuthenticationException {
        if (token == null)
            throw new TvDbAuthenticationException("Encountered non-existing token: " + token);
        setupHttpParamsForReAuthentication();
        String json = tvDbHttpRequest.get(uri, header);
        return createToken(json);
    }

    private String createToken(String json) throws TvDbAuthenticationException {
        checkJsonResponse(json);
        this.token = addTokenType(parseToken(json));
        return token;
    }

    private String parseToken(final String json) {
        return new JsonParser().parse(json).getAsJsonObject().get("token").getAsString();
    }

    private String addTokenType(final String parsedToken) {
        return "Bearer " + parsedToken;
    }

    private void checkJsonResponse(final String json) throws TvDbAuthenticationException {
        JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        final String memberError = "Error";
        if (jsonObject.has(memberError))
            throw new TvDbAuthenticationException(jsonObject.get(memberError)
                                                            .getAsString());
    }

    private void setupHttpParamsForAuthentication() throws TvDbException {
        this.uri = new TvDbUriResourceBuilder(TvDbUriResourceConstants.LOGIN).build();
        this.header = new BasicHeader(HttpHeaders.CONTENT_TYPE, "application/json");
    }

    private void setupHttpParamsForReAuthentication() throws TvDbException {
        this.uri = new TvDbUriResourceBuilder(TvDbUriResourceConstants.REFRESH_TOKEN).build();
        this.header = new BasicHeader(HttpHeaders.AUTHORIZATION, token);
    }
}
