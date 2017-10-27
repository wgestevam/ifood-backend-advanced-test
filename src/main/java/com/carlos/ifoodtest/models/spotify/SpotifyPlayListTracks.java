package com.carlos.ifoodtest.models.spotify;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

public class SpotifyPlayListTracks implements Serializable{

    private List<SpotifyPlaylistTrack> items;

    public List<SpotifyPlaylistTrack> getItems() {
        return items;
    }

    public void setItems(List<SpotifyPlaylistTrack> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SpotifyPlayListTracks that = (SpotifyPlayListTracks) o;

        return new EqualsBuilder()
                .append(items, that.items)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(items)
                .toHashCode();
    }
}
