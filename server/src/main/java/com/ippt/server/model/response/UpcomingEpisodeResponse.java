package com.ippt.server.model.response;

import com.ippt.server.entity.TvShowEpisode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpcomingEpisodeResponse {
    private long      episodeId;
    private long      showId;
    private String    episodeName;
    private String    showName;
    private int       episodeNumber;
    private int       seasonNumber;
    private String    showPoster;
    private LocalDate airDate;

    private UpcomingEpisodeResponse(TvShowEpisode entity) {
        this.setEpisodeId(entity.getId());
        this.setEpisodeName(entity.getName());
        this.setEpisodeNumber(entity.getEpisodeNumber());
        this.setSeasonNumber(entity.getSeasonNumber());
        this.setAirDate(entity.getAirDate());
    }

    public static UpcomingEpisodeResponse toResponse(TvShowEpisode entity) {
        return new UpcomingEpisodeResponse(entity);
    }
}
