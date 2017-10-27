package com.carlos.ifoodtest.models.spotify;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotifyTrack implements Serializable{
    private static final long serialVersionUID = -6734578320604824246L;

    private String name;
    private String id;
    private Map<String, Object> album;
    private List<Map<String, Object>> artists;

    @JsonProperty("external_urls")
    private Map<String, String> externalUrls;

    public SpotifyTrack() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getAlbum() {
        return album;
    }

    public void setAlbum(Map<String, Object> album) {
        this.album = album;
    }

    public List<Map<String, Object>> getArtists() {
        return artists;
    }

    public void setArtists(List<Map<String, Object>> artists) {
        this.artists = artists;
    }

    public Map<String, String> getExternalUrls() {
        return externalUrls;
    }

    public void setExternalUrls(Map<String, String> externalUrls) {
        this.externalUrls = externalUrls;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        SpotifyTrack that = (SpotifyTrack) o;

        return new EqualsBuilder()
                .append(name, that.name)
                .append(id, that.id)
                .append(album, that.album)
                .append(artists, that.artists)
                .append(externalUrls, that.externalUrls)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(name)
                .append(id)
                .append(album)
                .append(artists)
                .append(externalUrls)
                .toHashCode();
    }
}
