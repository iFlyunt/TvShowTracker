package com.tvshowtracker.service.impl;

import com.tvshowtracker.entity.Subscriber;
import com.tvshowtracker.entity.Subscription;
import com.tvshowtracker.entity.TvShow;
import com.tvshowtracker.repository.SubscriptionRepository;
import com.tvshowtracker.service.AbstractService;
import com.tvshowtracker.service.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionServiceImpl
        extends AbstractService<Subscription, Long, SubscriptionRepository>
        implements SubscriptionService {
    protected SubscriptionServiceImpl(@Autowired SubscriptionRepository repository) {
        super(repository);
    }

    @Override
    public void subscribe(Subscriber subscriber, TvShow tvShow) {
        Subscription subscription = new Subscription();
        subscription.setSubscriber(subscriber);
        subscription.setTvShow(tvShow);
        super.save(subscription);
    }

    @Override
    public Optional<Subscription> findSubscriptionByClientIdAndTvShowId(long clientId, long showId) {
        return getRepository().findSubscriptionByClientIdAndTvShowId(clientId, showId);
    }
}
