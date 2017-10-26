package com.carlos.ifoodtest.models.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyPlaylist {

    @JsonProperty("tracks")
    private SpotifyPlayListTracks tracks;
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public SpotifyPlayListTracks getTracks() {
        return tracks;
    }

    public void setTracks(SpotifyPlayListTracks tracks) {
        this.tracks = tracks;
    }
}
