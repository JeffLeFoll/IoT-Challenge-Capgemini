package info.lefoll.socle.persistance;

import info.lefoll.socle.requete.Agrégation;

import java.util.Optional;

public interface Connecteur {

    <T> void créerEntité(T donnée);

    <T> Optional<T> rechercherEntitéParId(String id);

    <T> Optional<T> effectuerRequête(Agrégation agrégation);

    <T> void pourLEntité(Class<T> typeDeLEntité);

}
