package iot.challenge.application.requete;

import com.google.common.collect.Lists;
import info.lefoll.socle.requete.Agrégation;
import info.lefoll.socle.requete.ConstructeurDOpérateur;
import info.lefoll.socle.requete.ManipulateurDeRequête;
import info.lefoll.socle.requete.OpérateurAgrégation;
import iot.challenge.application.depot.DépôtDeMessages;
import iot.challenge.application.modele.Synthesis;

import javax.inject.Inject;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ManipulateurSynthèseParCapteur implements ManipulateurDeRequête<SynthèseParCapteur, List<Synthesis>> {

    @Inject
    public ManipulateurSynthèseParCapteur(DépôtDeMessages dépôt) {
        this.dépôt = dépôt;
    }

    @Override
    public List<Synthesis> exécuter(SynthèseParCapteur requête) {

        Agrégation agrégation = fabriquerAgrégatSynthèse(requête.getDateRequête());

        List<Synthesis> synthèsesParCapteur = dépôt.calculerAgrégationSynthèse(agrégation);

        return synthèsesParCapteur;
    }

    public Agrégation fabriquerAgrégatSynthèse(Instant dateRequête) {

        Agrégation agrégation = new Agrégation();

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
