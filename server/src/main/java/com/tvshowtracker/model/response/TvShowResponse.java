package com.tvshowtracker.model.response;

import com.tvshowtracker.entity.TvShow;
import com.tvshowtracker.model.response.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

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
