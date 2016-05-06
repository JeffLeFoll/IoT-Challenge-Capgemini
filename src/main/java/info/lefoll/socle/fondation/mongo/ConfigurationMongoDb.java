package info.lefoll.socle.fondation.mongo;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.jongo.Jongo;
import org.jongo.Mapper;
import org.jongo.marshall.jackson.JacksonMapper;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.Instant;
import java.util.List;

import static org.jongo.marshall.jackson.JacksonMapper.*;

public class ConfigurationMongoDb {

    public Jongo clientJongo(){

        Mapper mapperJackson = new Builder().registerModule(new JavaTimeModule()).build();

        final DB db = fabriquerClientMongo().getDB(nom);

        return new Jongo(db, mapperJackson);
    }

    private MongoClient fabriquerClientMongo() {

        List<MongoCredential> infosDeIdentifications = Lists.newArrayList();

        if (avecAuthentificationBDD()) {
            MongoCredential infosDeIdentification = MongoCredential.createCredential(utilisateur, nom, motDePasse.toCharArray());
            infosDeIdentifications.add(infosDeIdentification);
        }

        return new MongoClient(Lists.newArrayList(serveurMongo()), infosDeIdentifications);
    }

    private ServerAddress serveurMongo(){
        return new ServerAddress(host, port);
    }

    private boolean avecAuthentificationBDD() {
        return !Strings.isNullOrEmpty(utilisateur);
    }

    @Inject
    @Named("db.host")
    public String host;

    @Inject
    @Named("db.nom")
    public String nom;

    @Inject
    @Named("db.utilisateur")
    public String utilisateur;

    @Inject
    @Named("db.motDePasse")
    public String motDePasse;

    @Inject
    @Named("db.port")
    public int port;

}
