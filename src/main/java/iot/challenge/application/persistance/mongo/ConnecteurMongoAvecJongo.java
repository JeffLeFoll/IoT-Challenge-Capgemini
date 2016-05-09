package iot.challenge.application.persistance.mongo;

import info.lefoll.socle.persistance.Connecteur;
import info.lefoll.socle.requete.Agrégation;
import iot.challenge.application.requete.mongodb.AgrégationMongoDB;
import iot.challenge.application.requete.mongodb.OpérateurAgrégation;
import org.jongo.Aggregate;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import javax.inject.Inject;
import java.util.Optional;

public class ConnecteurMongoAvecJongo implements Connecteur {

    @Inject
    public ConnecteurMongoAvecJongo(Jongo jongo) {
        this.jongo = jongo;
    }

    @Override
    public <T> void pourLEntité(Class<T> typeDeLEntité) {
        this.typeDeLEntité = typeDeLEntité;
    }

    @Override
    public <T> void créerEntité(T donnée) {
        collectionMongo().save(donnée);
    }

    @Override
    public <T> Optional<T> rechercherEntitéParId(String id) {

        T document = collectionMongo().findOne("{_id: #}", id).as((Class<T>) typeDeLEntité);

        return Optional.ofNullable(document);
    }

    @Override
    public Optional<Aggregate> effectuerRequête(Agrégation agrégation) {

        assert agrégation instanceof AgrégationMongoDB;

        OpérateurAgrégation match = ((AgrégationMongoDB) agrégation).getMatch();
        OpérateurAgrégation group = ((AgrégationMongoDB) agrégation).getGroup();

        Aggregate résultatAgrégation = collectionMongo()
                .aggregate(match.getEtape(), match.getParamètres())
                .and(group.getEtape());

        return Optional.ofNullable(résultatAgrégation);
    }

    private MongoCollection collectionMongo() {
        return jongo.getCollection(typeDeLEntité.getSimpleName());
    }

    private Jongo jongo;
    private Class<?> typeDeLEntité;
}
