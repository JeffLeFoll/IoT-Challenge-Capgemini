package iot.challenge.application.requete;

import info.lefoll.socle.requete.ManipulateurDeRequête;
import iot.challenge.application.depot.DépôtDeMessages;
import iot.challenge.application.modele.SynthèseGénérée;
import iot.challenge.application.requete.mongodb.AgrégationMongoDB;
import iot.challenge.application.requete.mongodb.ConstructeurDOpérateur;
import iot.challenge.application.requete.mongodb.OpérateurAgrégation;

import javax.inject.Inject;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ManipulateurSynthèseParCapteur implements ManipulateurDeRequête<SynthèseParCapteur, List<SynthèseGénérée>> {

    @Inject
    public ManipulateurSynthèseParCapteur(DépôtDeMessages dépôt) {
        this.dépôt = dépôt;
    }

    @Override
    public List<SynthèseGénérée> exécuter(SynthèseParCapteur requête) {

        AgrégationMongoDB agrégation = fabriquerAgrégatSynthèse(requête.getDateRequête());

        List<SynthèseGénérée> synthèsesParCapteur = dépôt.calculerAgrégationSynthèse(agrégation);

        return synthèsesParCapteur;
    }

    public AgrégationMongoDB fabriquerAgrégatSynthèse(Instant dateRequête) {

        AgrégationMongoDB agrégation = new AgrégationMongoDB();

        agrégation.setMatch(fabriquerMatch(dateRequête));
        agrégation.setGroup(fabriquerGroup());

        return agrégation;
    }

    private OpérateurAgrégation fabriquerMatch(Instant dateRequête) {
        OpérateurAgrégation match = new ConstructeurDOpérateur()
                .avecLeTypeEtape("$match")
                .avecLExpressionEtape("timestamp: { $gte: #, $lt: # }")
                .avecLesParamètres(calculerDateDébut(dateRequête), dateRequête)
                .construire();

        return match;
    }

    private Instant calculerDateDébut(Instant dateRequête) {

        return dateRequête.minus(1L, ChronoUnit.HOURS);
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

    private final DépôtDeMessages dépôt;
}
