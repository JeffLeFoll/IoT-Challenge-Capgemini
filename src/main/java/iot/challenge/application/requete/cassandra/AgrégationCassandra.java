package iot.challenge.application.requete.cassandra;

import info.lefoll.socle.requete.Agrégation;

import java.util.Objects;

public class AgrégationCassandra implements Agrégation {

    public String requêteSQL() {
        return requêteSQL;
    }

    public void ajouterRequêteSQL(String requêteSQL) {
        this.requêteSQL = requêteSQL;
    }

    public Object[] valeurs() {
        return valeurs;
    }

    public void ajouterValeurs(Objects... valeurs) {
        this.valeurs = valeurs;
    }

    private String requêteSQL;
    private Objects[] valeurs;
}
