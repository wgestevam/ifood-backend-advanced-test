package com.carlos.ifoodtest.models.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchSpotifyResponse implements Serializable {

    private static final long serialVersionUID = -5768678500724277852L;
    @JsonProperty("tracks")
    private SpotifyTracks spotifyTrack;

    @JsonProperty("playlists")
    private SpotifyPlayLists spotifyPlaylists;

    public SpotifyTracks getSpotifyTrack() {
        return spotifyTrack;
    }

    public void setSpotifyTrack(SpotifyTracks spotifyTrack) {
        this.spotifyTrack = spotifyTrack;
    }

    public SpotifyPlayLists getSpotifyPlaylists() {
        return spotifyPlaylists;
    }

    public void setSpotifyPlaylists(SpotifyPlayLists spotifyPlaylist) {
        this.spotifyPlaylists = spotifyPlaylist;
    }
}
