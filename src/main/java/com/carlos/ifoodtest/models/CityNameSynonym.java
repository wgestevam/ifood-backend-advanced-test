package com.carlos.ifoodtest.models;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.persistence.*;

@Entity
public class CityNameSynonym {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "city_id")
    private City city;
    private String synonym;

    public String getSynonym() {
        return synonym;
    }

    public void setSynonym(String query) {
        this.synonym = query;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City cityId) {
        this.city = cityId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        CityNameSynonym that = (CityNameSynonym) o;

        return new EqualsBuilder()
                .append(id, that.id)
                .append(city, that.city)
                .append(synonym, that.synonym)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(city)
                .append(synonym)
                .toHashCode();
    }
}
