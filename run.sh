#!/bin/bash

export env=prod

chmod +775 ./build/libs/IoT-Challenge-Capgemini.jar

java -cp ./build/dep/*:./build/libs/* -DPROD_MODE=true iot.challenge.application.ServeurApplication
