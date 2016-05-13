package iot.challenge.application.requete;

import info.lefoll.socle.requete.Requête;

import java.time.Instant;

public class SynthèseParCapteur implements Requête {

    public Instant getDateRequête() {
        return dateRequête;
    }

    public void setDateRequête(Instant dateRequête) {
        this.dateRequête = dateRequête;
    }

    public int getDurée() {
        return durée;
    }

    public void setDurée(int durée) {
        this.durée = durée;
    }

    private Instant dateRequête;
    private int durée;
}
