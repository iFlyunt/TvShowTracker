package com.ippt.http;

import org.apache.http.Header;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public final class TvTrackerRequest {

    public enum Method {
        GET("GET"),
        POST("POST"),
        DELETE("DELETE");

        private String methodName;
        Method(String methodName) {
            this.methodName = methodName;
        }

        public String getMethod() {
            return methodName;
        }
    }

    public static class Builder {
        private Method method;
        private List<Header> headerBuilder = new ArrayList<>();
        private URI    uriBuilder;
        private String body;

        public Builder setMethod(Method method) {
            this.method = method;
            return this;
        }

        public Builder addHeader(Header header) {
            this.headerBuilder.add(header);
            return this;
        }

        public Builder setUri(URI uriBuilder) {
            this.uriBuilder = uriBuilder;
            return this;
        }

        public Builder setBody(String body) {
            this.body = body;
            return this;
        }

        public TvTrackerRequest build() {
            return new TvTrackerRequest(this);
        }
    }

    private Method       method;
    private List<Header> headersBuilder;
    private URI          uri;
    private String       body;

    private TvTrackerRequest(Builder builder) {
        this.method = builder.method;
        this.headersBuilder = builder.headerBuilder;
        this.uri = builder.uriBuilder;
        this.body = builder.body;
    }

    public String getMethod() {
        return method.getMethod();
    }

    public List<Header> getHeaders() {
        return headersBuilder;
    }

    public URI getUri() {
        return uri;
    }

    public String getBody() {
        return body;
    }
}
