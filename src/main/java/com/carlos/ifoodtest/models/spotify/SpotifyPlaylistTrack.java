package com.carlos.ifoodtest.models.spotify;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

public class SpotifyPlaylistTrack implements Serializable {

    private static final long serialVersionUID = 5641243177539338660L;
    private SpotifyTrack track;

    public SpotifyTrack getTrack() {
        return track;
    }

    public void setTrack(SpotifyTrack track) {
        this.track = track;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SpotifyPlaylistTrack that = (SpotifyPlaylistTrack) o;

        return new EqualsBuilder()
                .append(track, that.track)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(track)
                .toHashCode();
    }
}
