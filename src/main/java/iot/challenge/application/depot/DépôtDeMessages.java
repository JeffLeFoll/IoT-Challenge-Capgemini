package iot.challenge.application.depot;

import com.google.common.collect.Lists;
import info.lefoll.socle.depot.Dépôt;
import info.lefoll.socle.persistance.Connecteur;
import info.lefoll.socle.requete.Agrégation;
import iot.challenge.application.modele.MessageReçut;
import iot.challenge.application.modele.SynthèseGénérée;
import iot.challenge.application.persistance.mongo.ConnecteurMongoAvecJongo;
import iot.challenge.application.persistance.mongo.MongoDB;
import iot.challenge.application.persistance.sql.ConnecteurSQL;
import iot.challenge.application.persistance.sql.SQL;
import iot.challenge.application.requete.mongodb.AgrégationMongoDB;
import iot.challenge.application.requete.sql.AgrégationSQL;
import org.jongo.Aggregate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class DépôtDeMessages implements Dépôt<MessageReçut> {

    @Inject
    public DépôtDeMessages(@SQL Connecteur connecteur) {
        this.connecteur = (ConnecteurSQL) connecteur;
        connecteur.pourLEntité(MessageReçut.class);
    }

    @Override
    public void créer(MessageReçut donnée) {

        AgrégationSQL agrégation = new AgrégationSQL();
        agrégation.ajouterRequêteSQL(donnée.insertSQL());
        agrégation.ajouterValeurs(donnée.getId(), donnée.getTimestamp(), donnée.getSensorType(), donnée.getValue());

        connecteur.créerEntité(agrégation);
    }

    @Override
    public Optional<MessageReçut> rechercherParId(String id) {

        AgrégationSQL agrégation = new AgrégationSQL();
        agrégation.ajouterRequêteSQL("SELECT * FROM Messages where id=?;");
        agrégation.ajouterValeurs(id);

        return connecteur.avecLeProcesseur(this::construireMessageReçut).effectuerRequête(agrégation);
    }

    public List<SynthèseGénérée> calculerAgrégationSynthèse(AgrégationSQL agrégation) {

        Optional<List<SynthèseGénérée>> résultatAgrégation = connecteur.avecLeProcesseur(this::construireSynthèseGénérée).effectuerRequête(agrégation);

        return résultatAgrégation.orElse(Lists.newArrayList());
    }

    private Optional<MessageReçut> construireMessageReçut(ResultSet rs) {
        try {
            if (rs != null && rs.next()) {
                MessageReçut messageReçut = new MessageReçut();
                messageReçut.setId(rs.getString("id"));
                messageReçut.setSensorType(rs.getInt("sensorType"));
                messageReçut.setTimestamp(Instant.from(DateTimeFormatter.ISO_INSTANT.parse(rs.getString("timestamp"))));
                messageReçut.setValue(rs.getLong("value"));

                return Optional.of(messageReçut);
            }
        } catch (SQLException e) {
            LOGGER.error("Erreur lors du mapping bdd -> MessageReçut. ", e);
        }
        return Optional.empty();
    }

    private Optional<List<SynthèseGénérée>> construireSynthèseGénérée(ResultSet rs) {
        try {
            if (rs != null) {
                List<SynthèseGénérée> synthèses = Lists.newArrayList();
                while(rs.next()){
                    SynthèseGénérée synthèse = new SynthèseGénérée();
                    synthèse.setSensorType(rs.getInt("sensorType"));
                    synthèse.setMinValue(rs.getLong("maxValue"));
                    synthèse.setMaxValue(rs.getLong("minValue"));
                    synthèse.setMediumValue(rs.getLong("mediumValue"));

                    synthèses.add(synthèse);
                }

                return Optional.of(synthèses);
            }
        } catch (SQLException e) {
            LOGGER.error("Erreur lors du mapping bdd -> SynthèseGénérée. ", e);
        }
        return Optional.empty();
    }

    private ConnecteurSQL connecteur;

    private static Logger LOGGER = LoggerFactory.getLogger(DépôtDeMessages.class);
}
