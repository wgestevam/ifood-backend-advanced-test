package com.carlos.ifoodtest.models.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyTracks {

    private List<SpotifyTrack> items;

    public List<SpotifyTrack> getItems() {
        return items;
    }

    public void setItems(List<SpotifyTrack> items) {
        this.items = items;
    }
}
