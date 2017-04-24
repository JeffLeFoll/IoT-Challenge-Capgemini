package iot.challenge.application.commande;

import info.lefoll.socle.commande.Commande;
import iot.challenge.application.modele.MessageReçut;

public class SupprimerMessage implements Commande {

    public static SupprimerMessage de(MessageReçut message) {
        return new SupprimerMessage(message);
    }

    public SupprimerMessage(MessageReçut message) {
        this.message = message;
    }

    public MessageReçut getMessage() {
        return message;
    }

    private MessageReçut message;

}
