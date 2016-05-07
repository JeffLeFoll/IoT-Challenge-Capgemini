package iot.challenge.application.requete;

import iot.challenge.application.requete.mongodb.AgrégationMongoDB;
import org.junit.Before;
import org.junit.Test;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

public class ManipulateurSynthèseParCapteurTest {

    private Instant dateRequête;
    private ManipulateurSynthèseParCapteur manipulateur;

    @Before
    public void setUp() throws Exception {
        dateRequête = Instant.from(DateTimeFormatter.ISO_INSTANT.parse("1985-04-12T21:15:50.520Z"));

        manipulateur = new ManipulateurSynthèseParCapteur(null);
    }

    @Test
    public void doitFabriquerLOpérateurMatch() {
        String opérateurMatchAttendu = "{$match: {timestamp: { $gte: #, $lt: # }}}";

        AgrégationMongoDB opérateurs = manipulateur.fabriquerAgrégatSynthèse(dateRequête);

        assertThat(opérateurs.getMatch().getEtape()).isEqualTo(opérateurMatchAttendu);
    }


    @Test
    public void doitFabriquerLOpérateurGroup() {
        String opérateurGroupAttendu = "{$group: {_id: '$sensorType'," +
                " minValue: {$min: '$value'}," +
                " maxValue: {$max: '$value'}," +
                " mediumValue: {$avg: '$value'}}}";

        AgrégationMongoDB opérateurs = manipulateur.fabriquerAgrégatSynthèse(dateRequête);

        assertThat(opérateurs.getGroup().getEtape()).isEqualTo(opérateurGroupAttendu);
    }


}