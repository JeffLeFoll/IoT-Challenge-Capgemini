package info.lefoll.socle.persistance;

import java.util.Optional;

public interface Connecteur {

    <T> void créerEntité(T donnée);

    <T> Optional<T> rechercherEntitéParId(String id);

    <T> void pourLEntité(Class<T> typeDeLEntité);

}
