package com.carlos.ifoodtest.models;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String link;
    private String artistName;
    private String sugestionList;
    private String externalId;

    public Track() {

    }

    public Track(String name, String link, String artistName) {
        this.name = name;
        this.link = link;
        this.artistName = artistName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSugestionList() {
        return sugestionList;
    }

    public void setSugestionList(String sugestionList) {
        this.sugestionList = sugestionList;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Track track = (Track) o;

        return new EqualsBuilder()
                .append(id, track.id)
                .append(name, track.name)
                .append(link, track.link)
                .append(artistName, track.artistName)
                .append(sugestionList, track.sugestionList)
                .append(externalId, track.externalId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(name)
                .append(link)
                .append(artistName)
                .append(sugestionList)
                .append(externalId)
                .toHashCode();
    }
}
