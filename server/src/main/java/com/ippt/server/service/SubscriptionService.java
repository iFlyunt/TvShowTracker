package com.ippt.server.service;

import com.ippt.server.entity.Subscriber;
import com.ippt.server.entity.Subscription;
import com.ippt.server.entity.TvShow;
import com.ippt.server.repository.SubscriptionRepository;

import java.util.Optional;

public interface SubscriptionService extends Service<Subscription, Long, SubscriptionRepository> {
    void subscribe (Subscriber subscriber, TvShow tvShow);

    Optional<Subscription> findSubscriptionByClientIdAndTvShowId(long clientId, long showId);
}
