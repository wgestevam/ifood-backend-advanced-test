package com.carlos.ifoodtest.models;

import com.vividsolutions.jts.geom.Point;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        City city = (City) o;

        return new EqualsBuilder()
                .append(temp, city.temp)
                .append(id, city.id)
                .append(externalId, city.externalId)
                .append(name, city.name)
                .append(cityNameSynonyms, city.cityNameSynonyms)
                .append(location, city.location)
                .append(updatedAt, city.updatedAt)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(externalId)
                .append(name)
                .append(temp)
                .append(cityNameSynonyms)
                .append(location)
                .append(updatedAt)
                .toHashCode();
    }
}
