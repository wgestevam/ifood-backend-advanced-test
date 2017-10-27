package com.carlos.ifoodtest.models.openwheater;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherResponse {
    @JsonProperty(required = false)
    private String query;
    private String name;
    private Integer id;
    @JsonProperty("main")
    private MainTemp mainTemp;
    @JsonProperty("coord")
    private Coordinates coordinates;

    public MainTemp getMainTemp() {
        return mainTemp;
    }

    public void setMainTemp(MainTemp mainTemp) {
        this.mainTemp = mainTemp;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        OpenWeatherResponse that = (OpenWeatherResponse) o;

        return new EqualsBuilder()
                .append(query, that.query)
                .append(name, that.name)
                .append(id, that.id)
                .append(mainTemp, that.mainTemp)
                .append(coordinates, that.coordinates)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(query)
                .append(name)
                .append(id)
                .append(mainTemp)
                .append(coordinates)
                .toHashCode();
    }
}
