package com.tvshowtracker.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter
@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Subscriber subscriber;

    @ManyToOne(fetch = FetchType.LAZY)
    private TvShow tvShow;

    public Subscription() {}

    public Subscription(Subscriber subscriber, TvShow tvShow) {
        this.subscriber = subscriber;
        this.tvShow = tvShow;
    }
}
