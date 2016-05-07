package iot.challenge.application.persistance.mongo;

import com.google.common.reflect.TypeToken;
import info.lefoll.socle.persistance.Connecteur;
import iot.challenge.application.requete.mongodb.AgrégationMongoDB;
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

        T document = collectionMongo().findOne("{_id: #}", id).as(déterminerLeTypeEntité());

        return Optional.ofNullable(document);
    }

    public Optional<Aggregate> effectuerAgrégation(AgrégationMongoDB agrégation) {

        Aggregate résultatAgrégation = collectionMongo().aggregate(agrégation.getMatch().getEtape(), agrégation.getMatch().getParamètres())
                .and(agrégation.getGroup().getEtape());

        return Optional.ofNullable(résultatAgrégation);
    }

    private MongoCollection collectionMongo() {
        return jongo.getCollection(typeDeLEntité.getSimpleName());
    }

    private <T> Class<T> déterminerLeTypeEntité() {

        TypeToken<T> type = new TypeToken<T>(getClass()) {
        };

        return (Class<T>) type.getRawType();
    }

    private Jongo jongo;
    private Class<?> typeDeLEntité;
}
