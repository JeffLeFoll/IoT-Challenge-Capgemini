package iot.challenge.application.commande;

import info.lefoll.socle.commande.ManipulateurDeCommande;
import iot.challenge.application.depot.DépôtDeMessages;

import javax.inject.Inject;

public class ManipulateurEnregistrerMessage implements ManipulateurDeCommande<EnregistrerMessage> {

    @Inject
    public ManipulateurEnregistrerMessage(DépôtDeMessages dépôt) {
        this.dépôt = dépôt;
    }

    @Override
    public void exécuter(EnregistrerMessage commande) {
        dépôt.créer(commande.getMessage());
    }

    private final DépôtDeMessages dépôt;
}
