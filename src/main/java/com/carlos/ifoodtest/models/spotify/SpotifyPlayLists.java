package com.carlos.ifoodtest.models.spotify;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;

public class SpotifyPlayLists implements Serializable {

    private static final long serialVersionUID = -6719474479766289553L;
    private List<SpotifyPlaylist> items;

    public List<SpotifyPlaylist> getItems() {
        return items;
    }

    public void setItems(List<SpotifyPlaylist> items) {
        this.items = items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SpotifyPlayLists that = (SpotifyPlayLists) o;

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
