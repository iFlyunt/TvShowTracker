package com.tvshowtracker.config;

import com.tvshowtracker.TvDbException;
import com.tvshowtracker.api.TvDb;
import com.tvshowtracker.api.auth.TvDbAuthentication;
import com.tvshowtracker.api.auth.TvDbAuthenticationException;
import com.tvshowtracker.api.auth.TvDbAuthenticationImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;

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
