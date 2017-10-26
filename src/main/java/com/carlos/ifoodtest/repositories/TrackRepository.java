package com.carlos.ifoodtest.repositories;

import com.carlos.ifoodtest.models.Track;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackRepository extends CrudRepository<Track, Integer> {

    Track findTrackBySugestionListAndExternalId(String sugestionList, String name);

    List<Track> findAllBySugestionList(String sugestionList);
}
