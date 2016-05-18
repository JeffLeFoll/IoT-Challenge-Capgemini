#!/bin/bash

export environment=prod

cp sqlite-patch/* build/dep/

chmod +775 ./build/libs/IoT-Challenge-Capgemini.jar

sudo java -cp ./build/dep/*:./build/libs/* -DPROD_MODE=true iot.challenge.application.ServeurApplication
