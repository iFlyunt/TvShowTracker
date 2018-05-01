package com.ippt.api.entity;

import com.ippt.parser.TvDbSeriesParser;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TvDbSeries extends AbstractTvDbEntity {
    private TvDbSeriesParser tvDbSeriesParser;

    public TvDbSeries(TvDbSeriesParser tvDbSeriesParser) {
        super(tvDbSeriesParser);
        this.tvDbSeriesParser = tvDbSeriesParser;
    }

    public String getName() {
        return tvDbSeriesParser.getName();
    }

    public String getStatus() {
        return tvDbSeriesParser.getStatus();
    }

    public String getAirsDayOfWeek() {
        return tvDbSeriesParser.getAirsDayOfWeek();
    }

    public Optional<LocalTime> getAirsTime() {
        String strTime = tvDbSeriesParser.getAirsTime();
        if (strTime.isEmpty())
            return Optional.empty();
        return Optional.of(LocalTime.parse(strTime,
                                           DateTimeFormatter.ofPattern("[h:mm a][HH:mm][hh:mm a]")));
    }

    public String getNetwork() {
        return tvDbSeriesParser.getNetwork();
    }

}
