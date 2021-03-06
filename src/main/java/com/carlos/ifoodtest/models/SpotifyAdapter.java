package com.carlos.ifoodtest.models;

import com.carlos.ifoodtest.models.spotify.SearchSpotifyResponse;
import com.carlos.ifoodtest.models.spotify.SpotifyPlaylist;
import com.carlos.ifoodtest.models.spotify.SpotifyPlaylistTrack;
import com.carlos.ifoodtest.models.spotify.SpotifyTrack;

import java.util.List;
import java.util.stream.Collectors;

public class SpotifyAdapter {

    public static List<Track> extractTracksFromPlayList(SpotifyPlaylist spotifyPlaylist) {

        List<SpotifyPlaylistTrack> spotifyTracks = spotifyPlaylist.getTracks().getItems();
        List<Track> tracks = spotifyTracks.stream().map(spotifyTrack ->
                extractTrackFrom(spotifyTrack.getTrack())
        ).collect(Collectors.toList());
        return tracks;
    }

    public static List<Track> extractTracksFromGenre(SearchSpotifyResponse searchSpotifyResponse) {

        List<SpotifyTrack> spotifyTracks = searchSpotifyResponse.getSpotifyTrack().getItems();
        return spotifyTracks.stream().map(SpotifyAdapter::extractTrackFrom
        ).collect(Collectors.toList());
    }

    private static Track extractTrackFrom(SpotifyTrack spotifyTrack) {
        Track track = new Track();
        track.setName(spotifyTrack.getName());
        track.setExternalId(spotifyTrack.getId());

        if(spotifyTrack.getArtists() != null
           && !spotifyTrack.getArtists().isEmpty()){
            String artistName = spotifyTrack.getArtists().stream().map(
                    stringObjectMap -> (String) stringObjectMap.get("name"))
                    .collect(Collectors.joining(", ", "", ""));
            track.setArtistName(artistName);
        }

        if(spotifyTrack.getExternalUrls() !=null &&
                !spotifyTrack.getExternalUrls().isEmpty()
                && spotifyTrack.getExternalUrls().containsKey("spotify")){
            track.setLink(spotifyTrack.getExternalUrls().get("spotify"));
        }

        return track;
    }


}

