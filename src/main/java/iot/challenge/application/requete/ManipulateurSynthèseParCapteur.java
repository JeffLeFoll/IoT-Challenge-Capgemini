package iot.challenge.application.requete;

import info.lefoll.socle.requete.Agrégation;
import info.lefoll.socle.requete.ManipulateurDeRequête;
import iot.challenge.application.depot.DépôtDeMessages;
import iot.challenge.application.modele.SynthèseGénérée;
import iot.challenge.application.requete.mongodb.AgrégationMongoDB;
import iot.challenge.application.requete.mongodb.ConstructeurDOpérateur;
import iot.challenge.application.requete.mongodb.OpérateurAgrégation;
import iot.challenge.application.requete.sql.AgrégationSQL;

import javax.inject.Inject;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ManipulateurSynthèseParCapteur implements ManipulateurDeRequête<SynthèseParCapteur, List<SynthèseGénérée>> {

    @Inject
    public ManipulateurSynthèseParCapteur(DépôtDeMessages dépôt) {
        this.dépôt = dépôt;
    }

    @Override
    public List<SynthèseGénérée> exécuter(SynthèseParCapteur requête) {

        Agrégation agrégation = fabriquerRequêteSynthèse(requête);

        List<SynthèseGénérée> synthèsesParCapteur = dépôt.calculerAgrégationSynthèse(agrégation);

        return synthèsesParCapteur;
    }


    public Agrégation fabriquerRequêteSynthèse(SynthèseParCapteur requête) {

        AgrégationMongoDB agrégation = new AgrégationMongoDB();

        agrégation.setMatch(fabriquerMatch(requête));
        agrégation.setGroup(fabriquerGroup());

        return agrégation;
    }

    private OpérateurAgrégation fabriquerMatch(SynthèseParCapteur requête) {
        OpérateurAgrégation match = new ConstructeurDOpérateur()
                .avecLeTypeEtape("$match")
                .avecLExpressionEtape("timestamp: { $gte: #, $lt: # }")
                .avecLesParamètres(DateTimeFormatter.ISO_INSTANT.format(requête.getDateRequête()), calculerDateFin(requête.getDateRequête(), requête.getDurée()))
                .construire();

        return match;
    }
    private OpérateurAgrégation fabriquerGroup() {
        OpérateurAgrégation group = new ConstructeurDOpérateur()
                .avecLeTypeEtape("$group")
                .avecLExpressionEtape("_id: '$sensorType'")
                .avecLAccumulateur(new OpérateurAgrégation.Accumulateur("minValue: {$min: '$value'},"))
                .avecLAccumulateur(new OpérateurAgrégation.Accumulateur("maxValue: {$max: '$value'},"))
                .avecLAccumulateur(new OpérateurAgrégation.Accumulateur("mediumValue: {$avg: '$value'}"))
                .construire();

        return group;
    }

    private String calculerDateFin(Instant dateRequête, int durée) {

        Instant dateFin = dateRequête.plus(durée, ChronoUnit.SECONDS);

        return DateTimeFormatter.ISO_INSTANT.format(dateFin);
    }

    private final DépôtDeMessages dépôt;
}
