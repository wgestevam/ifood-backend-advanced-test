package com.carlos.ifoodtest.consumers;

import com.carlos.ifoodtest.services.SpotifyService;
import com.carlos.ifoodtest.types.PlaylistType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TrackUpdaterConsumer {
    @Autowired
    private SpotifyService spotifyService;

    @JmsListener(destination = "update-tracks-by-name", containerFactory = "myFactory")
    public void updateGenre(PlaylistType playlistType) {
        spotifyService.updateTracksByGenre(playlistType);
    }
}
