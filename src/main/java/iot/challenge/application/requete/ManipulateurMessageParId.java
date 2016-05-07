package iot.challenge.application.requete;

import info.lefoll.socle.requete.ManipulateurDeRequête;
import iot.challenge.application.depot.DépôtDeMessages;
import iot.challenge.application.modele.MessageReçut;

import javax.inject.Inject;
import java.util.Optional;

public class ManipulateurMessageParId implements ManipulateurDeRequête<MessageParId, MessageReçut> {

    @Inject
    public ManipulateurMessageParId(DépôtDeMessages dépôt) {
        this.dépôt = dépôt;
    }

    @Override
    public MessageReçut exécuter(MessageParId requête) {

        Optional<MessageReçut> message = dépôt.rechercherParId(requête.getId());

        return message.orElse(null);
    }

    private final DépôtDeMessages dépôt;
}
