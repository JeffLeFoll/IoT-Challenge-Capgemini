package iot.challenge.application.depot;

import com.google.common.collect.Lists;
import iot.challenge.application.requete.mongodb.AgrégationMongoDB;
import iot.challenge.application.modele.Message;
import info.lefoll.socle.depot.Dépôt;
import iot.challenge.application.persistance.mongo.ConnecteurMongoAvecJongo;
import iot.challenge.application.modele.Synthesis;
import org.jongo.Aggregate;
import org.jongo.Jongo;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class DépôtDeMessages implements Dépôt<Message> {

    @Inject
    public DépôtDeMessages(ConnecteurMongoAvecJongo connecteur) {
        this.connecteur=connecteur;
        connecteur.pourLEntité(Message.class);
    }

    @Override
    public void créer(Message donnée) {
        connecteur.créerEntité(donnée);
    }

    @Override
    public Optional<Message> rechercherParId(String id) {
        return connecteur.rechercherEntitéParId(id);
    }

    public List<Synthesis> calculerAgrégationSynthèse(AgrégationMongoDB agrégation) {
        List<Synthesis> ensembleDeSynthèses = Lists.newArrayList();

        Optional<Aggregate> résultatAgrégation = connecteur.effectuerAgrégation(agrégation);

        résultatAgrégation.ifPresent(agrégat -> agrégat.as(Synthesis.class).forEach(ensembleDeSynthèses::add));

        return ensembleDeSynthèses;
    }

    private ConnecteurMongoAvecJongo connecteur;
}
