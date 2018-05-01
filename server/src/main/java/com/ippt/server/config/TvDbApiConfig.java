package com.ippt.server.config;

import com.ippt.TvDbException;
import com.ippt.api.TvDb;
import com.ippt.api.auth.TvDbAuthentication;
import com.ippt.api.auth.TvDbAuthenticationException;
import com.ippt.api.auth.TvDbAuthenticationImpl;
import com.ippt.api.auth.TvDbCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.util.stream.Collectors.joining;

@Configuration
public class TvDbApiConfig {
    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public TvDb tvDbBean()
            throws IOException, TvDbAuthenticationException, TvDbException, URISyntaxException {
        InputStream inputStream = resourceLoader.getResource("classpath:thetvdb_credentials")
                                                .getInputStream();
        String credentials = new BufferedReader(new InputStreamReader(inputStream))
                .lines().collect(joining());
        String token = tvDbAuthentication().authenticate(credentials);
        return new TvDb(token);
    }

    @Bean
    public TvDbAuthentication tvDbAuthentication() {
        return new TvDbAuthenticationImpl();
    }
}
