package com.tvshowtracker.service;

import com.tvshowtracker.entity.Subscriber;
import com.tvshowtracker.entity.Subscription;
import com.tvshowtracker.entity.TvShow;
import com.tvshowtracker.repository.SubscriptionRepository;

import java.util.Optional;

public interface SubscriptionService extends Service<Subscription, Long, SubscriptionRepository> {
    void subscribe (Subscriber subscriber, TvShow tvShow);

    Optional<Subscription> findSubscriptionByClientIdAndTvShowId(long clientId, long showId);
}
