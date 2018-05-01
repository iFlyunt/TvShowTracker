package com.tvshowtracker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "episode")
public class TvShowEpisode extends AbstractTvDbEntity {
    @Column(name = "air_date")
    private LocalDate airDate;

    @Column(name = "episode_number")
    private int episodeNumber;

    @Column(name = "season_number")
    private int seasonNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private TvShow tvShow;

    @OneToMany(mappedBy = "episode", cascade = CascadeType.REMOVE)
    private List<WatchedEpisode> watchedEpisodes;
}
