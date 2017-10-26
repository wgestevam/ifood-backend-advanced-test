package com.carlos.ifoodtest.models;

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
}
