package com.carlos.ifoodtest.models;

public class TrackDTO {

    private final String artist;
    private final String link;
    private final String name;

    public TrackDTO(Track track){
        this.name = track.getName();
        this.link = track.getLink();
        this.artist = track.getArtistName();
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public String getArtist() {
        return artist;
    }
}
