package info.lefoll.socle.fondation.mongo;

import com.google.common.reflect.TypeToken;
import info.lefoll.socle.requete.Agrégation;
import info.lefoll.socle.requete.OpérateurAgrégation;
import org.jongo.Aggregate;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.util.List;
import java.util.Optional;

public abstract class ConnecteurMongoAvecJongo<TypeEntité> {

    public ConnecteurMongoAvecJongo(Jongo jongo) {

        String typeEntité = déterminerLeTypeEntité().getSimpleName();

        collection = jongo.getCollection(typeEntité);
    }

    public void créerEntité(TypeEntité donnée) {
        collection.save(donnée);
    }

    public Optional<TypeEntité> rechercherEntitéParId(String id) {

        TypeEntité document = collection.findOne("{_id: #}", id).as(déterminerLeTypeEntité());

        return Optional.ofNullable(document);
    }

    public Optional<Aggregate> effectuerAgrégation(Agrégation agrégation) {

        Aggregate résultatAgrégation = collection.aggregate(agrégation.getMatch().getEtape(), agrégation.getMatch().getParamètres())
                .and(agrégation.getGroup().getEtape());


        return Optional.ofNullable(résultatAgrégation);
    }

    private Class<TypeEntité> déterminerLeTypeEntité() {

        TypeToken<TypeEntité> type = new TypeToken<TypeEntité>(getClass()) {
        };

        return (Class<TypeEntité>) type.getRawType();
    }

    private MongoCollection collection;
}
