#!/bin/bash

chmod +x /home/pi/dev/IoT-Challenge-Capgemini/build/libs/IoT-Challenge-Capgemini.jar

sudo mongod --dbpath /home/pi/dev/data/db

sudo java -cp /home/pi/dev/IoT-Challenge-Capgemini/build/dep/*:/home/pi/dev/IoT-Challenge-Capgemini/build/libs/* -DPROD_MODE=true -Denv=prod iot.challenge.application.ServeurApplication

