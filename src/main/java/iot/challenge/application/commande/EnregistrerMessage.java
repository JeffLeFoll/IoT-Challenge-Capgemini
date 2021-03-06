package iot.challenge.application.commande;

import info.lefoll.socle.commande.Commande;
import iot.challenge.application.modele.MessageReçut;

public class EnregistrerMessage implements Commande {

  public static EnregistrerMessage de(MessageReçut message) {
    return new EnregistrerMessage(message);
  }
  
    public EnregistrerMessage(MessageReçut message) {
        this.message = message;
    }

    public MessageReçut getMessage() {
        return message;
    }
    
    private MessageReçut message;
}
