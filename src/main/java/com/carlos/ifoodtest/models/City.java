package com.carlos.ifoodtest.models;

import com.vividsolutions.jts.geom.Point;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Integer externalId;
    private String name;
    private int temp;
    @OneToMany(mappedBy = "city")
    private List<CityNameSynonym> cityNameSynonyms;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getExternalId() {
        return externalId;
    }

    public void setExternalId(Integer externalId) {
        this.externalId = externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<CityNameSynonym> getCityNameSynonyms() {
        return cityNameSynonyms;
    }

    public void setCityNameSynonyms(List<CityNameSynonym> cityNameSynonyms) {
        this.cityNameSynonyms = cityNameSynonyms;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }
}
