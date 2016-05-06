package iot.challenge.application.depot;

import com.google.common.collect.Lists;
import info.lefoll.socle.requete.Agrégation;
import info.lefoll.socle.requete.OpérateurAgrégation;
import iot.challenge.application.modele.Message;
import info.lefoll.socle.depot.Dépôt;
import info.lefoll.socle.fondation.mongo.ConnecteurMongoAvecJongo;
import iot.challenge.application.modele.Synthesis;
import org.jongo.Aggregate;
import org.jongo.Jongo;

import javax.inject.Inject;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class DépôtDeMessages extends ConnecteurMongoAvecJongo<Message> implements Dépôt<Message> {

    @Inject
    public DépôtDeMessages(Jongo jongo) {
        super(jongo);
    }

    @Override
    public void créer(Message donnée) {
        this.créerEntité(donnée);
    }

    @Override
    public Optional<Message> rechercherParId(String id) {
        return this.rechercherEntitéParId(id);
    }

    public List<Synthesis> calculerAgrégationSynthèse(Agrégation agrégation) {
        List<Synthesis> ensembleDeSynthèses = Lists.newArrayList();

        Optional<Aggregate> résultatAgrégation = this.effectuerAgrégation(agrégation);

        résultatAgrégation.ifPresent(agrégat -> agrégat.as(Synthesis.class).forEach(ensembleDeSynthèses::add));

        return ensembleDeSynthèses;
    }

}
