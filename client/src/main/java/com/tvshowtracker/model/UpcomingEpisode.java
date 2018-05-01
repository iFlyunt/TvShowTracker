package com.tvshowtracker.model;

public class UpcomingEpisode extends AbstractEpisode {
    public static class Builder extends AbstractEpisode.Builder<Builder> {
        private long showId;
        private String showName;
        private String showPosterUrl;

        public Builder setShowName(String showName) {
            this.showName = showName;
            return this;
        }

        public Builder setShowPosterUrl(String showPosterUrl) {
            this.showPosterUrl = showPosterUrl;
            return this;
        }

        public Builder setShowId(long showId) {
            this.showId = showId;
            return this;
        }

        public UpcomingEpisode build() {
            return new UpcomingEpisode(this);
        }
    }

    private long showId;
    private String showName;
    private String showPosterUrl;

    private UpcomingEpisode(Builder builder) {
        super(builder);
        this.showName = builder.showName;
        this.showPosterUrl = builder.showPosterUrl;
        this.showId = builder.showId;
    }

    public String getShowName() {
        return this.showName;
    }

    public String getShowPosterUrl() {
        return this.showPosterUrl;
    }

    public long getShowId() {
        return this.showId;
    }

    @Override
    public String toString() {
        return "UpcomingEpisode{" + "showId=" + showId + ", showName='" + showName + '\''
               + ", showPosterUrl='" + showPosterUrl + '\'' + "} " + super.toString();
    }
}
