package com.ippt.server.service.impl;

import com.ippt.server.entity.Subscriber;
import com.ippt.server.entity.TvShowEpisode;
import com.ippt.server.entity.WatchedEpisode;
import com.ippt.server.repository.WatchedEpisodeRepository;
import com.ippt.server.service.AbstractService;
import com.ippt.server.service.TvEpisodeService;
import com.ippt.server.service.UserService;
import com.ippt.server.service.WatchedEpisodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dsig.keyinfo.X509IssuerSerial;
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
