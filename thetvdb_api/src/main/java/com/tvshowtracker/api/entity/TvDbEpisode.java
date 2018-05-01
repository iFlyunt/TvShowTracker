package com.tvshowtracker.api.entity;

import com.tvshowtracker.parser.TvDbEpisodeParser;

import java.time.LocalDate;
import java.util.Optional;

public class TvDbEpisode extends AbstractTvDbEntity {
    private TvDbEpisodeParser tvDbEpisodeParser;

    public TvDbEpisode(TvDbEpisodeParser tvDbEpisodeParser) {
        super(tvDbEpisodeParser);
        this.tvDbEpisodeParser = tvDbEpisodeParser;
    }

    public String getName() {
        return tvDbEpisodeParser.getName();
    }

    public Optional<LocalDate> getAirDate() {
        String strTime = tvDbEpisodeParser.getAirDate();
        if(strTime.isEmpty())
            return Optional.empty();
        return Optional.of(LocalDate.parse(strTime));
    }

    public int getEpisodeNumber() {
        return tvDbEpisodeParser.getEpisodeNumber();
    }

    public int getSeasonNumber() { return tvDbEpisodeParser.getSeasonNumber(); }
}
