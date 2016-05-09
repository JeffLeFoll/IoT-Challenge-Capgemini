package iot.challenge.application.depot;

import com.google.common.collect.Lists;
import info.lefoll.socle.depot.Dépôt;
import info.lefoll.socle.persistance.Connecteur;
import iot.challenge.application.modele.MessageReçut;
import iot.challenge.application.modele.SynthèseGénérée;
import iot.challenge.application.persistance.mongo.ConnecteurMongoAvecJongo;
import iot.challenge.application.persistance.mongo.MongoDB;
import iot.challenge.application.requete.mongodb.AgrégationMongoDB;
import org.jongo.Aggregate;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class DépôtDeMessages implements Dépôt<MessageReçut> {

    @Inject
    public DépôtDeMessages(@MongoDB Connecteur connecteur) {
        this.connecteur = connecteur;
        connecteur.pourLEntité(MessageReçut.class);
    }

    @Override
    public void créer(MessageReçut donnée) {
        connecteur.créerEntité(donnée);
    }

    @Override
    public Optional<MessageReçut> rechercherParId(String id) {
        return connecteur.rechercherEntitéParId(id);
    }

    public List<SynthèseGénérée> calculerAgrégationSynthèse(AgrégationMongoDB agrégation) {
        List<SynthèseGénérée> ensembleDeSynthèses = Lists.newArrayList();

        Optional<Aggregate> résultatAgrégation = connecteur.effectuerRequête(agrégation);

        résultatAgrégation.ifPresent(agrégat -> agrégat.as(SynthèseGénérée.class).forEach(ensembleDeSynthèses::add));

        return ensembleDeSynthèses;
    }

    private Connecteur connecteur;
}
