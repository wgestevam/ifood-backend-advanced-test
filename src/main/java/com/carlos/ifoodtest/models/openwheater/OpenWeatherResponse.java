package com.carlos.ifoodtest.models.openwheater;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

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
}
