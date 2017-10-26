package com.carlos.ifoodtest.services;

import com.carlos.ifoodtest.models.City;
import com.carlos.ifoodtest.models.CityNameSynonym;
import com.carlos.ifoodtest.models.openwheater.OpenWeatherResponse;
import com.carlos.ifoodtest.repositories.CityNameSynonymRepository;
import com.carlos.ifoodtest.repositories.CityRepository;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.vividsolutions.jts.geom.Point;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class WheaterService {
    private static final Logger log = LoggerFactory.getLogger(WheaterService.class);

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CityNameSynonymRepository cityNameSynonymRepository;


    @Autowired
    private JmsTemplate jmsTemplate;
    private String appID = "015b491ac7ca9836a8f35be2d56e69e8";

    public int getTemperatureByCity(String cityName) {


        City city = cityRepository.findBySynonym(cityName);

        if (city != null) {
            LocalDateTime localDate = LocalDateTime.now().minusMinutes(15);
            Date fifteenMinutesAgo = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
            if (city.getUpdatedAt().before(fifteenMinutesAgo)) {
                return getWheater(cityName).getMainTemp().getTemp();
            } else {
                return city.getTemp();
            }
        } else {
            return getWheater(cityName).getMainTemp().getTemp();
        }


    }

    public OpenWeatherResponse getWheater(String city) {
        RestTemplate restTemplate = new RestTemplate();
        OpenWeatherResponse openWeatherResponse = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q=" + city
                + "&appid="+appID+"&units=metric", OpenWeatherResponse.class);
        openWeatherResponse.setQuery(city);
        jmsTemplate.convertAndSend("create-update-city  ", openWeatherResponse);
        return openWeatherResponse;
    }

    public OpenWeatherResponse getWheaterByLatLon(Double lat, Double lon) {
        RestTemplate restTemplate = new RestTemplate();
        OpenWeatherResponse openWeatherResponse = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?lat=" + lat
                + "&lon=" + lon+ "&appid="+appID+"&units=metric", OpenWeatherResponse.class);

        jmsTemplate.convertAndSend("create-update-city", openWeatherResponse);
        return openWeatherResponse;
    }

    public void updateCity(OpenWeatherResponse openWeatherResponse) {
        City city = cityRepository.findByExternalId(openWeatherResponse.getId());

        if (city == null) {
            city = new City();
            city.setName(openWeatherResponse.getName());
            city.setExternalId(openWeatherResponse.getId());
        }
        city.setTemp(openWeatherResponse.getMainTemp().getTemp());
        city.setUpdatedAt(new Date());

        Coordinate coordinate =new Coordinate(
                openWeatherResponse.getCoordinates().getLon(),
                openWeatherResponse.getCoordinates().getLat());
        Point point = new GeometryFactory().createPoint(coordinate);
        point.setSRID(4326);

        city.setLocation(point);
        cityRepository.save(city);

        if(!StringUtils.isEmpty(openWeatherResponse.getQuery())){
            jmsTemplate.convertAndSend("update-city-synonym", openWeatherResponse);
        }
    }

    @Transactional
    public void updateCitySynonym(OpenWeatherResponse openWheater) {
        CityNameSynonym cityNameSynonym = cityNameSynonymRepository.findBySynonym(openWheater.getQuery());
        if (cityNameSynonym == null) {
            cityNameSynonym = new CityNameSynonym();
            City city = cityRepository.findByExternalId(openWheater.getId());
            cityNameSynonym.setCity(city);

            cityNameSynonym.setSynonym(openWheater.getQuery());
            if (cityNameSynonym.getCity() == null) {
                throw new RuntimeException("cidade não disponivel");
            }
            cityNameSynonymRepository.save(cityNameSynonym);
        }
    }

    public int getTemperatureByLatLon(Double lat, Double lon) {
        GeometryFactory geometryFactory = new GeometryFactory();
        Point point = geometryFactory.createPoint(new Coordinate(lon,lat));
        point.setSRID(4326);
        List<City> cities = cityRepository.listCitiesNear(point.getX(),point.getY());


        if (cities != null && !cities.isEmpty()) {
            LocalDateTime localDate = LocalDateTime.now().minusMinutes(15);
            City city = cities.get(0);
            Date fifteenMinutesAgo = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
            if (city.getUpdatedAt().before(fifteenMinutesAgo)) {
                return getWheaterByLatLon(lat,lon).getMainTemp().getTemp();
            } else {
                return city.getTemp();
            }
        } else {
            return getWheaterByLatLon(lat,lon).getMainTemp().getTemp();
        }

    }
}
