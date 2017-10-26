package com.carlos.ifoodtest.models;

import com.carlos.ifoodtest.models.spotify.SpotifyPlayListTracks;
import com.carlos.ifoodtest.models.spotify.SpotifyPlaylist;
import com.carlos.ifoodtest.models.spotify.SpotifyPlaylistTrack;
import com.carlos.ifoodtest.models.spotify.SpotifyTrack;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class SpotifyAdapterTest {
    @Test
    public void extractTracksFromPlayList() throws Exception {
        SpotifyPlaylist spotifyPlaylist = new SpotifyPlaylist();
        SpotifyPlayListTracks spotifyPlayListTrack = new SpotifyPlayListTracks();
        List<SpotifyPlaylistTrack> items = new ArrayList<>();
        SpotifyPlaylistTrack item1 = new SpotifyPlaylistTrack();
        SpotifyTrack spotifyTrack = new SpotifyTrack();
        spotifyTrack.setName("track name");
        item1.setTrack(spotifyTrack);
        items.add(item1);
        spotifyPlayListTrack.setItems(items);
        spotifyPlaylist.setTracks(spotifyPlayListTrack);

        List<Track> tracks = SpotifyAdapter.extractTracksFromPlayList(spotifyPlaylist);

        Assert.assertEquals(tracks.get(0).getName(),"track name");

    }

    @Test
    public void extractTracksFromGenre() throws Exception {

    }

}