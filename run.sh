#!/bin/bash

cp /home/pi/dev/IoT-Challenge-Capgemini/sqlite-patch/* /home/pi/dev/IoT-Challenge-Capgemini/build/dep/

chmod +x /home/pi/dev/IoT-Challenge-Capgemini/build/libs/IoT-Challenge-Capgemini.jar

sudo java -cp /home/pi/dev/IoT-Challenge-Capgemini/build/dep/*:/home/pi/dev/IoT-Challenge-Capgemini/build/libs/* -DPROD_MODE=true -Denv=prod iot.challenge.application.ServeurApplication

