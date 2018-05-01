package com.ippt.model;

import java.time.LocalDate;

public abstract class AbstractEpisode extends AbstractEntity {

    public static abstract class Builder<B extends AbstractEntity.Builder<B>>
            extends AbstractEntity.Builder<B> {
        private String airDate;
        private int    episodeNumber;
        private int    seasonNumber;
        private B      self;

        protected Builder() {
            this.self = (B) this;
        }

        public B setAirDate(String airDate) {
            this.airDate = airDate;
            return self;
        }

        public B setEpisodeNumber(int episodeNumber) {
            this.episodeNumber = episodeNumber;
            return self;
        }

        public B setSeasonNumber(int seasonNumber) {
            this.seasonNumber = seasonNumber;
            return self;
        }
    }

    private String airDate;
    private int    episodeNumber;
    private int    seasonNumber;

    protected AbstractEpisode(Builder builder) {
        super(builder);
        this.airDate = builder.airDate;
        this.episodeNumber = builder.episodeNumber;
        this.seasonNumber = builder.seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public String getAirDate() {
        return airDate;
    }

    @Override
    public String toString() {
        return "AbstractEpisode{" + "airDate='" + airDate + '\'' + ", episodeNumber="
               + episodeNumber + ", seasonNumber=" + seasonNumber + "} " + super.toString();
    }
}
