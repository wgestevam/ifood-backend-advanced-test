package com.carlos.ifoodtest.services;

import com.carlos.ifoodtest.models.Track;
import com.carlos.ifoodtest.types.PlaylistType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class SugestionService {

    private static final Logger logger = LoggerFactory.getLogger(SugestionService.class);
    @Autowired
    private WheaterService wheaterService;

    @Autowired
    private SpotifyService spotifyService;


    public List<Track> sugestTracksByCity(String city) {

        Instant instant = Instant.now();
        Integer temperature = wheaterService.getTemperatureByCity(city);
        Instant instant2 = Instant.now();
        long gap = ChronoUnit.MILLIS.between(instant, instant2);
        logger.debug("Tempo gasto pegar temperatura: " + gap);

        PlaylistType playlistType = PlaylistType.getEnumByTemperature(temperature);

        return spotifyService.sugestTracksByWheater(playlistType);

    }

    public List<Track> sugestTracksByCoordenates(Double lat, Double lon) {
        int temp =  wheaterService.getTemperatureByLatLon(lat,lon);
        PlaylistType playlistType = PlaylistType.getEnumByTemperature(temp);
        return spotifyService.sugestTracksByWheater(playlistType);
    }
}
