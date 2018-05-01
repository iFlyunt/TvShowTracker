package com.ippt.service.impl;

import com.ippt.entity.Subscriber;
import com.ippt.entity.TvShowEpisode;
import com.ippt.entity.WatchedEpisode;
import com.ippt.repository.WatchedEpisodeRepository;
import com.ippt.service.AbstractService;
import com.ippt.service.TvEpisodeService;
import com.ippt.service.UserService;
import com.ippt.service.WatchedEpisodeService;
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
