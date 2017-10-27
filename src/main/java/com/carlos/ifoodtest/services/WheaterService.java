package com.carlos.ifoodtest.services;

import com.carlos.ifoodtest.models.City;
import com.carlos.ifoodtest.models.CityNameSynonym;
import com.carlos.ifoodtest.models.openwheater.OpenWeatherResponse;
import com.carlos.ifoodtest.repositories.CityNameSynonymRepository;
import com.carlos.ifoodtest.repositories.CityRepository;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

@Service
public class WheaterService {
    private static final Logger logger = LoggerFactory.getLogger(WheaterService.class);
    public static final String OPEN_WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";

    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private CityNameSynonymRepository cityNameSynonymRepository;

    @Autowired
    RestTemplate restTemplate;


    @Autowired
    private JmsTemplate jmsTemplate;
    @Value("${openweather.id}")
    private String appID;

    public int getTemperatureByCity(String cityName) {

        City city = safelyGetCity(cityName);

        if (city != null) {
            return getUpdatedTemperature(city, () -> getWheater(cityName) );
        } else {
            return getWheater(cityName).getMainTemp().getTemp();
        }
    }

    private City safelyGetCity(String cityName) {
        City city = null;
        try{
            city = cityRepository.findBySynonym(cityName);
        } catch (Exception ex){
            logger.error("Não foi poissível recuperar a cidade ",ex);
        }
        return city;
    }

    private int getUpdatedTemperature(City city, Supplier<OpenWeatherResponse> openWeatherResponseSupplier) {
        LocalDateTime localDate = LocalDateTime.now().minusMinutes(15);
        Date fifteenMinutesAgo = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());

        if (city.getUpdatedAt().before(fifteenMinutesAgo)) {
            return openWeatherResponseSupplier.get().getMainTemp().getTemp();
        } else {
            return city.getTemp();
        }
    }

    private OpenWeatherResponse getWheater(String city) {

        OpenWeatherResponse openWeatherResponse = restTemplate.getForObject(OPEN_WEATHER_API_URL+"?q=" + city
                + "&appid="+appID+"&units=metric", OpenWeatherResponse.class);
        openWeatherResponse.setQuery(city);
        jmsTemplate.convertAndSend("create-update-city  ", openWeatherResponse);
        return openWeatherResponse;
    }

    private OpenWeatherResponse getWheaterByLatLon(Double lat, Double lon) {

        OpenWeatherResponse openWeatherResponse = restTemplate.getForObject(OPEN_WEATHER_API_URL +"?lat=" + lat
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

        Coordinate coordinate = new Coordinate(
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
            City city = cities.get(0);
            return getUpdatedTemperature(city, () -> getWheaterByLatLon(lat, lon) );
        } else {
            return getWheaterByLatLon(lat,lon).getMainTemp().getTemp();
        }

    }
}
