package info.lefoll.socle.requete;

import org.junit.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

public class ConstructeurDOpérateurTest {

    @Test
    public void peutConstruireUnOpérateurAgrégationVide(){
        
        OpérateurAgrégation opérateurAgrégation = new ConstructeurDOpérateur().construire();

        assertThat(opérateurAgrégation.getEtape()).isEqualTo("");
    }

    @Test
    public void peutConstruireUnOpérateurAgrégationSimple(){
        Instant dateDebut = Instant.now().minus(1l, ChronoUnit.HOURS);
        Instant dateFin = Instant.now();

        OpérateurAgrégation opérateurAgrégationSimple = new ConstructeurDOpérateur()
                .avecLeTypeEtape("$match")
                .avecLExpressionEtape("timestamp: { $gte: #, $lt: # }")
                .avecLesParamètres(dateDebut, dateFin)
                .construire();

        assertThat(opérateurAgrégationSimple.getEtape()).isEqualTo("{$match: {timestamp: { $gte: #, $lt: # }}}");
        assertThat(opérateurAgrégationSimple.getParamètres()).containsOnly(dateDebut, dateFin);
    }

    @Test
    public void peutConstruireUnOpérateurAgrégationAvecAccumulateurCorrectementFormaté(){

        OpérateurAgrégation opérateurAgrégationComplexe = new ConstructeurDOpérateur()
                .avecLeTypeEtape("$group")
                .avecLExpressionEtape("name:'Doe'")
                .avecLExpressionEtape("surename: 'John'")
                .avecLAccumulateur(new OpérateurAgrégation.Accumulateur("total: {$sum: '$montant'}"))
                .construire();

        assertThat(opérateurAgrégationComplexe.getEtape()).isEqualTo("{$group: {name:'Doe', surename: 'John', total: {$sum: '$montant'}}}");
    }

}