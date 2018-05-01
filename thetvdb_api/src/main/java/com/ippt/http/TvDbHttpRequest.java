package com.ippt.http;

import com.ippt.TvDbException;
import org.apache.http.Header;

import java.net.URI;
import java.util.List;

public interface TvDbHttpRequest {
    String get(URI uri, Header... headers) throws TvDbException;

    List<String> get(List<URI> uris, Header... headers) throws TvDbException;

    String post(URI uri, String json, Header... headers) throws TvDbException;
}
