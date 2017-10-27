# iFood Backend Advanced Test

Create a micro-service able to accept RESTful requests receiving as parameter either city name or lat long coordinates and returns a playlist (only track names is fine) suggestion according to the current temperature.

## Business rules

* If temperature (celcius) is above 30 degrees, suggest tracks for party
* In case temperature is above 15 and below 30 degrees, suggest pop music tracks
* If it's a bit chilly (between 10 and 14 degrees), suggest rock music tracks
* Otherwise, if it's freezing outside, suggests classical music tracks 

## Hints

You can make usage of OpenWeatherMaps API (https://openweathermap.org) to fetch temperature data and Spotify (https://developer.spotify.com) to suggest the tracks as part of the playlist.

## Non functional requirements

As this service will be a worldwide success,it must be prepared to be fault tolerant,responsive and resilient.

Use whatever language, tools and frameworks you feel comfortable to. 

Also, briefly elaborate on your solution, architecture details, choice of patterns and frameworks.

Fork this repository and submit your code.

## Requirements to run

* Java 8
* Maven
* Docker 
* Docker compose

### Requiments to develop

* Maven
* Java 8
* Postgresql with posgis


### Instrunctions to run
Clone this repository
```
$ git clone git@github.com:cadorfo/ifood-backend-advanced-test.git 
$ cd ifood-backend-advanced-test

```

#### execute with docker
Execute this file in repository root directory
```
$ ./createDocker.sh
```
#### execute with springboot maven plugin

You may have postgres with postgis plugin installed!

Change content of file `src/main/resources/application-dev.properties`

Execute the following command
```
$ mvn spring-boot:run
```
Endpoints:

* `http://localhost:8080/sugestions?city=Campinas`
* `http://localhost:8080/sugestions?lat=-15.7801&lon=-47.92`

