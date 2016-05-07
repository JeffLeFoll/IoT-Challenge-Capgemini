package info.lefoll.socle.requete;

import iot.challenge.application.requete.mongodb.ConstructeurDOpérateur;
import iot.challenge.application.requete.mongodb.OpérateurAgrégation;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class OpérateurAgrégationMongoDBTest {

    private OpérateurAgrégation opérateurAgrégation;

    @Before
    public void setUp() throws Exception {
        opérateurAgrégation = new OpérateurAgrégation();
    }

    @Test
    public void doitPouvoirAjouterUneValeur(){
        String valeur = "sender:1";

        opérateurAgrégation.ajouterLesParamètres(valeur);

        assertThat(opérateurAgrégation.getParamètres()[0]).isEqualTo(valeur);
    }

    @Test
    public void doitFormaterUnOpérateurAgrégationSimple(){
        opérateurAgrégation.setTypeEtape("$match");
        opérateurAgrégation.ajouterExpressionEtape("tags: 'read'");

        String étape = opérateurAgrégation.getEtape();

        assertThat(étape).isEqualTo("{$match: {tags: 'read'}}");
    }

    @Test
    public void peutConstruireUnOpérateurAgrégationAvecAccumulateurCorrectementFormaté(){

        OpérateurAgrégation opérateurAgrégationComplexe = new ConstructeurDOpérateur()
                .avecLeTypeEtape("$group")
                .avecLExpressionEtape("name:'Doe'")
                .avecLExpressionEtape("surename: 'John'")
                .avecLAccumulateur(new OpérateurAgrégation.Accumulateur("total: {$sum: '$montant'}"))
                .construire();

        String étape = opérateurAgrégationComplexe.getEtape();

        assertThat(étape).isEqualTo("{$group: {name:'Doe', surename: 'John', total: {$sum: '$montant'}}}");
    }

    @Test
    public void peutConstruireUneAgrégationVide(){
        OpérateurAgrégation opérateurAgrégation = new OpérateurAgrégation();

        String étape = opérateurAgrégation.getEtape();

        assertThat(étape).isEqualTo("");
    }

}