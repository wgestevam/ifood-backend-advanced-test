package com.carlos.ifoodtest.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class TrackDTO implements Serializable {

    private static final long serialVersionUID = -5116585595089878254L;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        TrackDTO trackDTO = (TrackDTO) o;

        return new EqualsBuilder()
                .append(artist, trackDTO.artist)
                .append(link, trackDTO.link)
                .append(name, trackDTO.name)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(artist)
                .append(link)
                .append(name)
                .toHashCode();
    }
}
