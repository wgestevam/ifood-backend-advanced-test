package com.carlos.ifoodtest.models.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchSpotifyResponse {

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
