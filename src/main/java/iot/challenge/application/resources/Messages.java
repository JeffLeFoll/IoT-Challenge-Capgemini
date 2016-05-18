package iot.challenge.application.resources;

import info.lefoll.socle.commande.BusDeCommande;
import info.lefoll.socle.requete.BusDeRequête;
import iot.challenge.application.commande.EnregistrerMessage;
import iot.challenge.application.modele.MessageReçut;
import iot.challenge.application.modele.swagger.Message;
import iot.challenge.application.modele.swagger.Synthesis;
import iot.challenge.application.requete.MessageParId;
import iot.challenge.application.requete.SynthèseParCapteur;
import net.codestory.http.annotations.AllowOrigin;
import net.codestory.http.annotations.Get;
import net.codestory.http.annotations.Post;
import net.codestory.http.annotations.Prefix;
import net.codestory.http.errors.NotFoundException;
import net.codestory.http.payload.Payload;

import javax.inject.Inject;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Prefix("/messages")
public class Messages {

    @Inject
    public Messages(BusDeCommande busDeCommande, BusDeRequête busDeRequête) {
        this.busDeCommande = busDeCommande;
        this.busDeRequête = busDeRequête;
    }

    @Post("")
    @AllowOrigin("*")
    public Payload enregistrerMessage(MessageReçut message) {

        busDeCommande.traiterCommande(new EnregistrerMessage(message));

        return new Payload(200);
    }

    @Get("/synthesis?timestamp=:heureDebut&duration=:période")
    public List<Synthesis> générerSynthèsePourLaDurée(String heureDebut, int période) {

        Instant dateRequête = Instant.from(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(heureDebut));

        SynthèseParCapteur requêteSynthèseParCapteur = new SynthèseParCapteur();
        requêteSynthèseParCapteur.setDateRequête(dateRequête);
        requêteSynthèseParCapteur.setDurée(période);

        List<Synthesis> synthèseDeChaqueCapteurs = (List<Synthesis>) busDeRequête.traiterRequête(requêteSynthèseParCapteur);

        return NotFoundException.notFoundIfNull(synthèseDeChaqueCapteurs);
    }

    private BusDeCommande busDeCommande;
    private BusDeRequête busDeRequête;
}
