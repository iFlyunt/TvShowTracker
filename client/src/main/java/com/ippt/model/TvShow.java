package com.ippt.model;

import com.ippt.http.RequestSender;

public class TvShow extends AbstractEntity {

    public static class Builder extends AbstractEntity.Builder<Builder>{
        private String airsDay;
        private String time;
        private String    network;
        private String    posterUrl;
        private int       seasonCount;
        private String    status;
        private boolean   subscribed;

        public Builder setAirsDay(String airsDay) {
            this.airsDay = airsDay;
            return this;
        }

        public Builder setTime(String time) {
            this.time = time;
            return this;
        }

        public Builder setNetwork(String network) {
            this.network = network;
            return this;
        }

        public Builder setPosterUrl(String posterUrl) {
            this.posterUrl = posterUrl;
            return this;
        }

        public Builder setSeasonCount(int seasonCount) {
            this.seasonCount = seasonCount;
            return this;
        }

        public Builder setStatus(String status) {
            this.status = status;
            return this;
        }

        public Builder setSubscribed(boolean subscribed) {
            this.subscribed = subscribed;
            return this;
        }

        public TvShow build() {
            return new TvShow(this);
        }
    }

    private String airsDay;
    private String time;
    private String    network;
    private String    posterUrl;
    private int       seasonCount;
    private String    status;
    private boolean   subscribed;

    private TvShow(Builder builder) {
        super(builder);
        this.airsDay = builder.airsDay;
        this.time = builder.time;
        this.network = builder.network;
        this.posterUrl = builder.posterUrl;
        this.seasonCount = builder.seasonCount;
        this.status = builder.status;
        this.subscribed = builder.subscribed;
    }

    public String getAirsDay() {
        return airsDay;
    }

    public String getTime() {
        return time;
    }

    public String getNetwork() {
        return network;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public int getSeasonCount() {
        return seasonCount;
    }

    public String getStatus() {
        return status;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    @Override
    public String toString() {
        return "TvShow{" + "airsDay='" + airsDay + '\'' + ", time='" + time + '\'' + ", network='"
               + network + '\'' + ", posterUrl='" + posterUrl + '\'' + ", seasonCount="
               + seasonCount + ", status='" + status + '\'' + ", subscribed=" + subscribed + "} "
               + super.toString();
    }
}
