package iot.challenge.application.requete;

import info.lefoll.socle.requete.Requête;

public class MessageParId implements Requête{

    public MessageParId(String id) {
        this.id=id;
    }

    public String getId() {
        return id;
    }

    private String id;
}
