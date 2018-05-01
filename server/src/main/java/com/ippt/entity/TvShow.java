package com.ippt.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "tv_show")
public class TvShow extends AbstractTvDbEntity {
    private String network;

    private String status;

    @Column(name = "poster_url")
    private String posterUrl;

    @Column(name = "air_day_of_week")
    private String airsDayOfWeek;

    @Column(name = "air_time", columnDefinition = "time")
    private LocalTime airsTime;

    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.REMOVE)
    private List<TvShowEpisode> tvShowEpisodes;

    @OneToMany(mappedBy = "tvShow", cascade = CascadeType.REMOVE)
    private List<Subscription> subscriptions;
}
