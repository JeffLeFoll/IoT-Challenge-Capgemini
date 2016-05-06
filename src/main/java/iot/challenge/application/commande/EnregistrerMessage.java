package iot.challenge.application.commande;

import iot.challenge.application.modele.Message;
import info.lefoll.socle.commande.Commande;

public class EnregistrerMessage implements Commande {

    public EnregistrerMessage(Message message) {
        this.message=message;
    }

    public Message getMessage() {
        return message;
    }

    private Message message;
}
