package com.tvshowtracker.service.impl;

import com.tvshowtracker.entity.Subscriber;
import com.tvshowtracker.entity.TvShowEpisode;
import com.tvshowtracker.entity.WatchedEpisode;
import com.tvshowtracker.repository.WatchedEpisodeRepository;
import com.tvshowtracker.service.AbstractService;
import com.tvshowtracker.service.TvEpisodeService;
import com.tvshowtracker.service.UserService;
import com.tvshowtracker.service.WatchedEpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchedEpisodeServiceImpl
        extends AbstractService<WatchedEpisode, Long, WatchedEpisodeRepository>
        implements WatchedEpisodeService {
    @Autowired
    private UserService userService;

    @Autowired
    private TvEpisodeService episodeService;

    protected WatchedEpisodeServiceImpl(@Autowired WatchedEpisodeRepository repository) {
        super(repository);
    }

    @Override
    public Optional<WatchedEpisode> getWatchedEpisode(long userId, long episodeId) {
        return getRepository().getEpisodeStatus(userId, episodeId);
    }

    @Override
    public void markAsWatched(Subscriber user, long episodeId) {
        Optional<WatchedEpisode> optionalEpisode = this.getWatchedEpisode(user.getId(), episodeId);
        optionalEpisode.ifPresent(we -> we.setWatched(!we.isWatched()));
        super.save(optionalEpisode.orElseGet(() -> createWatchedEpisodeEntry(user, episodeId)));
    }

    private WatchedEpisode createWatchedEpisodeEntry(Subscriber user, long episodeId) {
        TvShowEpisode episode = episodeService.findById(episodeId);
        WatchedEpisode watchedEpisode = new WatchedEpisode();
        watchedEpisode.setSubscriber(user);
        watchedEpisode.setEpisode(episode);
        watchedEpisode.setWatched(true);
        return watchedEpisode;
    }
}
