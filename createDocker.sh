#!/bin/bash

mvn clean install -Dspring.profiles.active=dev
docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)
docker build .
docker-compose up
