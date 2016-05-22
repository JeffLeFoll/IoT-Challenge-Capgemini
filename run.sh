#!/bin/bash

cp sqlite-patch/* build/dep/

chmod +775 ./build/libs/IoT-Challenge-Capgemini.jar

sudo java -cp ./build/dep/*;./build/libs/* -DPROD_MODE=true -Denv=dev iot.challenge.application.ServeurApplication
