package iot.challenge.application.requete;

import iot.challenge.application.depot.DépôtDeMessages;
import iot.challenge.application.modele.Message;
import info.lefoll.socle.requete.ManipulateurDeRequête;

import javax.inject.Inject;
import java.util.Optional;

public class ManipulateurMessageParId implements ManipulateurDeRequête<MessageParId, Message> {

    @Inject
    public ManipulateurMessageParId(DépôtDeMessages dépôt) {
        this.dépôt = dépôt;
    }

    @Override
    public Message exécuter(MessageParId requête) {

        Optional<Message> message = dépôt.rechercherParId(requête.getId());

        return message.orElse(null);
    }

    private final DépôtDeMessages dépôt;
}
