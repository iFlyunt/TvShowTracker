package com.tvshowtracker.repository;

import com.tvshowtracker.entity.Subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository <Subscription, Long> {
    @Query("select s from Subscription s where s.subscriber.id = ?1 and s.tvShow.id = ?2")
    Optional<Subscription> findSubscriptionByClientIdAndTvShowId(long clientId, long showId);
}
