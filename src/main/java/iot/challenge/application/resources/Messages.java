package iot.challenge.application.resources;

import info.lefoll.socle.commande.BusDeCommande;
import info.lefoll.socle.requete.BusDeRequête;
import iot.challenge.application.commande.EnregistrerMessage;
import iot.challenge.application.modele.MessageReçut;
import iot.challenge.application.modele.swagger.Message;
import iot.challenge.application.modele.swagger.Synthesis;
import iot.challenge.application.requete.MessageParId;
import iot.challenge.application.requete.SynthèseParCapteur;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;
import net.codestory.http.annotations.Prefix;
import net.codestory.http.errors.NotFoundException;

import javax.inject.Inject;
import java.time.Instant;
import java.util.List;

@Prefix("/messages")
public class Messages {

    @Inject
    public Messages(BusDeCommande busDeCommande, BusDeRequête busDeRequête) {
        this.busDeCommande = busDeCommande;
        this.busDeRequête = busDeRequête;
    }

    @Post("/")
    public void enregistrerMessage(MessageReçut message) {

        busDeCommande.traiterCommande(new EnregistrerMessage(message));
    }

    @Get("/:id")
    public Message rechercherParId(String id) {

        Message message = (Message) busDeRequête.traiterRequête(new MessageParId(id));

        return NotFoundException.notFoundIfNull(message);
    }

    @Get("/synthesis")
    public List<Synthesis> générerSynthèse() {

        List<Synthesis> synthèseDeChaqueCapteurs = (List<Synthesis>) busDeRequête.traiterRequête(new SynthèseParCapteur(Instant.now()));

        return NotFoundException.notFoundIfNull(synthèseDeChaqueCapteurs);
    }

    @Get("/synthesis?timestamp=:heureDebut&duration=:période")
    public List<Synthesis> générerSynthèsePourLaDurée(Instant heureDebut, int période) {

        List<Synthesis> synthèseDeChaqueCapteurs = (List<Synthesis>) busDeRequête.traiterRequête(new SynthèseParCapteur(Instant.now()));

        return NotFoundException.notFoundIfNull(synthèseDeChaqueCapteurs);
    }

    private BusDeCommande busDeCommande;
    private BusDeRequête busDeRequête;
}
