package iot.challenge.application.persistance.couchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.document.JsonDocument;
import com.couchbase.client.java.document.RawJsonDocument;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.lefoll.socle.persistance.Connecteur;
import info.lefoll.socle.requete.Agrégation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;

public class ConnecteurCouchBase implements Connecteur {

    @Inject
    public ConnecteurCouchBase(Bucket bucketCouchBase){
        this.bucketCouchBase = bucketCouchBase;
         jacksonMapper = new ObjectMapper();
    }

    @Override
    public <T> void pourLEntité(Class<T> typeDeLEntité) {
        this.typeDeLEntité = typeDeLEntité;
    }


    @Override
    public <T> void créerEntité(T donnée) {
        try {
            String json = jacksonMapper.writeValueAsString(donnée);
            RawJsonDocument document =  RawJsonDocument.create(json);
            bucketCouchBase.insert(document);

        } catch (JsonProcessingException e) {
            LOGGER.error("Erreur lors de l'insertion de l'objet " + donnée.toString(), e);
        }
    }

    public <T> void créerEntitéAvecId(T donnée, String id) {
        try {
            String json = jacksonMapper.writeValueAsString(donnée);
            RawJsonDocument document =  RawJsonDocument.create(id, json);
            bucketCouchBase.insert(document);

        } catch (JsonProcessingException e) {
            LOGGER.error("Erreur lors de l'insertion de l'objet " + donnée.toString(), e);
        }
    }

    @Override
    public <T> Optional<T> rechercherEntitéParId(String id) {

        T entité = null;
        try {
            JsonDocument document = bucketCouchBase.get(id);

            if(document != null) {
                entité = jacksonMapper.readValue(document.content().toString(), (Class<T>) typeDeLEntité);
            }
        } catch (IOException e) {
            LOGGER.error("Erreur lors de la récupération de l'objet ID:" + id, e);
        }

        return Optional.ofNullable(entité);
    }

    @Override
    public <T> Optional<T> effectuerRequête(Agrégation agrégation) {
        return null;
    }

    private Bucket bucketCouchBase;
    private ObjectMapper jacksonMapper;
    private Class<?> typeDeLEntité;

    private static Logger LOGGER = LoggerFactory.getLogger(ConnecteurCouchBase.class);
}
