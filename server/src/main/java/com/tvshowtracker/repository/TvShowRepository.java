package com.tvshowtracker.repository;

import com.tvshowtracker.entity.TvShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TvShowRepository extends JpaRepository<TvShow, Long> {
   @Query("select ts"
          + " from TvShow ts"
          + " join ts.subscriptions s"
          + " where s.subscriber.id = :id")
   List<TvShow> findAllShowsByUserId(@Param("id") long id);

   @Query ("select count(distinct e.seasonNumber)"
           + " from TvShow ts"
           + " join ts.tvShowEpisodes e where ts.id = ?1")
   int getSeasonCount(long showId);

   @Query("select ts from TvShow ts where ts.name like %?1%")
   List<TvShow> findShowsByName(String name);

   @Query("select ts from TvShow ts where ts.tvdbId = ?1")
   TvShow findShowByTvDbId(long tvDbId);

   @Query("select ts"
          + " from TvShow ts"
          + " join ts.subscriptions s"
          + " where s.subscriber.id = ?1"
          + " and ts.status = 'continuing'")
   List<TvShow> findRunningShowsByUserId(long userId);
}
