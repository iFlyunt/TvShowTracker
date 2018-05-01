package com.ippt.service;

import com.ippt.entity.Subscriber;
import com.ippt.entity.Subscription;
import com.ippt.entity.TvShow;
import com.ippt.repository.SubscriptionRepository;

import java.util.Optional;

public interface SubscriptionService extends Service<Subscription, Long, SubscriptionRepository> {
    void subscribe (Subscriber subscriber, TvShow tvShow);

    Optional<Subscription> findSubscriptionByClientIdAndTvShowId(long clientId, long showId);
}
