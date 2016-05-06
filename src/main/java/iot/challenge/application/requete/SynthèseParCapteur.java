package iot.challenge.application.requete;

import info.lefoll.socle.requete.Requête;

import java.time.Instant;

public class SynthèseParCapteur implements Requête {

    public SynthèseParCapteur(Instant dateRequête) {
        this.dateRequête = dateRequête;
    }

    public Instant getDateRequête() {
        return dateRequête;
    }

    private Instant dateRequête;
}
