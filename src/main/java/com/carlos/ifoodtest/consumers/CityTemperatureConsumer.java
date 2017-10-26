package com.carlos.ifoodtest.consumers;

import com.carlos.ifoodtest.models.openwheater.OpenWeatherResponse;
import com.carlos.ifoodtest.services.WheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class CityTemperatureConsumer {

    @Autowired
    private WheaterService wheaterService;

    @JmsListener(destination = "create-update-city", containerFactory = "myFactory")
    public void receiveMessage(OpenWeatherResponse openWheater) {
        wheaterService.updateCity(openWheater);
    }

    @JmsListener(destination = "update-city-synonym", containerFactory = "myFactory")
    public void receiveMessageSynonym(OpenWeatherResponse openWheater) {
        wheaterService.updateCitySynonym(openWheater);
    }
}
