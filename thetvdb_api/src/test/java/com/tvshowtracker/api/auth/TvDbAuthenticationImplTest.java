package com.tvshowtracker.api.auth;

import com.tvshowtracker.TvDbException;
import com.tvshowtracker.http.TvDbHttpRequest;
import org.apache.http.Header;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URI;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TvDbAuthenticationImplTest {
    private static final String ERROR_JSON_RESPONSE = "{\"Error\":\"test\"}";
    private static final String FAKE_CREDENTIALS    = "dummy";
    private static final String TOKEN_TYPE_BEARER   = "Bearer";
    private static final String TOKEN_PREFIX        = "\"token\":";

    @InjectMocks
    private TvDbAuthenticationImpl tvDbAuthentication;

    @Mock
    private TvDbHttpRequest tvDbHttpRequestMock;

    @Before
    public void setUp() throws TvDbException {
        when(tvDbHttpRequestMock.post(any(URI.class), eq(FAKE_CREDENTIALS), any(Header[].class)))
                .thenReturn("{" + TOKEN_PREFIX + "\"test_token\"}");
        when(tvDbHttpRequestMock.get(any(URI.class), any(Header[].class)))
                .thenReturn("{" + TOKEN_PREFIX + "\"test_token_new\"}");
    }

    @Test
    public void authenticateReturnsValidToken()
            throws TvDbException, TvDbAuthenticationException {
        String actualToken = tvDbAuthentication.authenticate(FAKE_CREDENTIALS);
        assertThat(actualToken, equalTo(TOKEN_TYPE_BEARER + " test_token"));
    }

    @Test
    public void reAuthenticateReturnsValidToken()
            throws TvDbAuthenticationException, TvDbException {
        tvDbAuthentication.authenticate(FAKE_CREDENTIALS);
        String actualToken = tvDbAuthentication.reAuthenticate();
        assertThat(actualToken, equalTo(TOKEN_TYPE_BEARER + " test_token_new"));
    }

    @Test
    public void reAuthenticateReturnsNewToken() throws TvDbAuthenticationException, TvDbException {
        String oldToken = tvDbAuthentication.authenticate(FAKE_CREDENTIALS);
        String newToken = tvDbAuthentication.reAuthenticate();
        assertThat(oldToken, not(newToken));
    }


    @Test(expected = TvDbAuthenticationException.class)
    public void authenticateWithInvalidCredentials()
            throws TvDbException, TvDbAuthenticationException {
        when(tvDbHttpRequestMock.post(any(URI.class), eq(FAKE_CREDENTIALS), any(Header[].class)))
                .thenReturn(ERROR_JSON_RESPONSE);
        tvDbAuthentication.authenticate(FAKE_CREDENTIALS);
    }

    @Test(expected = TvDbAuthenticationException.class)
    public void reAuthenticateWithInvalidToken() throws TvDbException, TvDbAuthenticationException {
        when(tvDbHttpRequestMock.get(any(URI.class), any(Header[].class)))
                .thenReturn(ERROR_JSON_RESPONSE);
        tvDbAuthentication.reAuthenticate();
    }
}
