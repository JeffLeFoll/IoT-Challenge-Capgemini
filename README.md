# IoT-Challenge-Capgemini [![Build Status](https://travis-ci.org/JeffLeFoll/IoT-Challenge-Capgemini.svg?branch=master)](https://travis-ci.org/JeffLeFoll/IoT-Challenge-Capgemini)
Ma solution pour le challenge IoT Capgemini (https://github.com/ltoinel/IoT-Development-Challenge)

# Solution full Java8
Serveur :
- [fluent-htpp](https://github.com/CodeStory/fluent-http)

Injection de dépendances
- [Reflections](https://github.com/ronmamo/reflections)
- Guice + Guice-multibindings

Persistance
- SQLite3 avec sqlite-jdbc

## Utilisation
Pour compiler le projet avec gradle et démarrer le serveur :
Unix:
```bash
./gradlew stage

java -cp ./build/dep/*:./build/libs/* iot.challenge.application.ServeurApplication
```
Windows
```bash
gradlew stage

java -cp ./build/dep/*;./build/libs/* iot.challenge.application.ServeurApplication
```