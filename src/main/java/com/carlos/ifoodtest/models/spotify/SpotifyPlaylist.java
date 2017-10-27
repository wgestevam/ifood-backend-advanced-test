package com.carlos.ifoodtest.models.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyPlaylist implements Serializable{

    private static final long serialVersionUID = 2049603834250210196L;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SpotifyPlaylist that = (SpotifyPlaylist) o;

        return new EqualsBuilder()
                .append(tracks, that.tracks)
                .append(href, that.href)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(tracks)
                .append(href)
                .toHashCode();
    }
}
