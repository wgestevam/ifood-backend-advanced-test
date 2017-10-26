package com.carlos.ifoodtest.controllers;

import com.carlos.ifoodtest.models.Track;
import com.carlos.ifoodtest.services.SugestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@RestController
public class WheaterController {

    private static final Logger logger = LoggerFactory.getLogger(WheaterController.class);
    @Autowired
    private SugestionService sugestionService;

    @RequestMapping("sugestions")
    public List<Track> getWheaterResponse(@RequestParam(value = "city", required = false) String city,
                                          @RequestParam(value = "lat", required = false) Double lat,
                                          @RequestParam(value = "lon", required = false) Double lon
    ) {

        long gap = 0;

        List<Track> tracks = null;
        if(StringUtils.isEmpty(city)){

            Instant instant = Instant.now();
            tracks = sugestionService.sugestTracksByCoordenates(lat,lon);
            Instant instant2 = Instant.now();
            gap = ChronoUnit.MILLIS.between(instant, instant2);

        } else{

            Instant instant = Instant.now();
            tracks = sugestionService.sugestTracksByCity(city);
            Instant instant2 = Instant.now();
            gap = ChronoUnit.MILLIS.between(instant, instant2);
        }


        logger.debug("Tempo gasto total: " + gap);
        return tracks;
    }
}
