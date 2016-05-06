package info.lefoll.socle.depot;

import java.util.Optional;

public interface Dépôt<TypeEntité> {

    void créer(TypeEntité donnée);

    Optional<TypeEntité> rechercherParId(String id);

}
