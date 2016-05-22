package iot.challenge.application.requete;

import info.lefoll.socle.requete.ManipulateurDeRequête;
import iot.challenge.application.depot.DépôtDeMessages;
import iot.challenge.application.modele.SynthèseGénérée;
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

        AgrégationSQL agrégation = fabriquerRequêteSynthèse(requête);

        List<SynthèseGénérée> synthèsesParCapteur = dépôt.calculerAgrégationSynthèse(agrégation);

        return synthèsesParCapteur;
    }

    public AgrégationSQL fabriquerRequêteSynthèse(SynthèseParCapteur requête) {

        String reqêteSQL = "SELECT " +
            "sensorType as sensorType, " +
            "max(value) as maxValue, " +
            "min(value) as minValue, " +
            "round(avg(value),2) as mediumValue " +
            "FROM 'Messages' " +
            "WHERE strftime('%s', timestamp) BETWEEN strftime('%s', ?) AND strftime('%s', ?) " +
            "Group by sensorType;";

        String dateDébut = DateTimeFormatter.ISO_INSTANT.format(requête.getDateRequête());
        String dateFin = calculerDateFin(requête.getDateRequête(), requête.getDurée());

        AgrégationSQL agrégation = new AgrégationSQL();
        agrégation.ajouterRequêteSQL(reqêteSQL);
        agrégation.ajouterValeurs(dateDébut, dateFin);

        return agrégation;
    }

    private String calculerDateFin(Instant dateRequête, int durée) {

        Instant dateFin = dateRequête.plus(durée, ChronoUnit.SECONDS);

        return DateTimeFormatter.ISO_INSTANT.format(dateFin);
    }

    private final DépôtDeMessages dépôt;
}
