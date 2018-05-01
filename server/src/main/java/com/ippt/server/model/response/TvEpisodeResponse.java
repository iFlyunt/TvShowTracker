package com.ippt.server.model.response;

import com.ippt.server.entity.TvShowEpisode;
import com.ippt.server.model.response.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Optional;

import static com.ippt.server.model.response.util.DateUtil.toStringFormat;

@Getter
@Setter
public class TvEpisodeResponse {
    private long   id;
    private String name;
    private String description;
    private String airDate;
    private int episodeNumber;
    private int seasonNumber;
    private boolean watched;

    private TvEpisodeResponse(TvShowEpisode tvShowEpisode) {
        this.setId(tvShowEpisode.getId());
        this.setName(tvShowEpisode.getName());
        this.setDescription(tvShowEpisode.getDescription());
        this.setAirDate(DateUtil.toStringFormat(tvShowEpisode.getAirDate()).orElse(""));
        this.setEpisodeNumber(tvShowEpisode.getEpisodeNumber());
        this.setSeasonNumber(tvShowEpisode.getSeasonNumber());
    }

    public static TvEpisodeResponse toResponse(TvShowEpisode entity) {
        return new TvEpisodeResponse(entity);
    }
}
