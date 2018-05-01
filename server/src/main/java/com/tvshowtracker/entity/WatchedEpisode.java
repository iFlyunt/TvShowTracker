package com.tvshowtracker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "watched_episode")
public class WatchedEpisode extends AbstractEntity {
    @Column(columnDefinition = "bit default false")
    private boolean watched;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subscriber subscriber;

    @ManyToOne(fetch = FetchType.LAZY)
    private TvShowEpisode episode;
}
