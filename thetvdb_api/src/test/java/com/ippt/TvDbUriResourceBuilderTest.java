package com.ippt;

import org.apache.http.message.BasicNameValuePair;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TvDbUriResourceBuilderTest {
    private TvDbUriResourceBuilder tvDbUriResourceBuilder;

    @Before
    public void setUp() throws Exception {
        tvDbUriResourceBuilder = new TvDbUriResourceBuilder("test");
    }

    @Test
    public void setParamAddsQueryParametersToUriResource() throws TvDbException {
        URI uri = tvDbUriResourceBuilder.setParam(new BasicNameValuePair("p", "v"))
                                        .build();
        assertThat(uri.getQuery(), equalTo("p=v"));
    }
}
