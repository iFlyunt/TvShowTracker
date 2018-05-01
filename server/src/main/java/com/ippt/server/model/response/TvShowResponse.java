package com.ippt.server.model.response;

import com.google.gson.internal.bind.TimeTypeAdapter;
import com.ippt.server.entity.TvShow;
import com.ippt.server.model.response.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;

@Getter
@Setter
public class TvShowResponse {
    private long      id;
    private String    name;
    private String    description;
    private String    airsDayOfWeek;
    private String airsTime;
    private String    network;
    private String    posterUrl;
    private int       seasonCount;
    private String    status;
    private boolean   subscribed;

    public TvShowResponse() {}

    private TvShowResponse(TvShow tvShow) {
        this.setId(tvShow.getId());
        this.setName(tvShow.getName());
        this.setDescription(tvShow.getDescription());
        this.setAirsDayOfWeek(tvShow.getAirsDayOfWeek());
        this.setAirsTime(DateUtil.to12HourFormat(tvShow.getAirsTime()).orElse(""));
        this.setNetwork(tvShow.getNetwork());
        this.setPosterUrl(tvShow.getPosterUrl());
        this.setStatus(tvShow.getStatus());
    }

    public static TvShowResponse toResponse(TvShow entity) {
        return new TvShowResponse(entity);
    }
}
