package iot.challenge.application.requete.sql;

import info.lefoll.socle.requete.Agrégation;

public class AgrégationSQL implements Agrégation {

    public String requêteSQL() {
        return requêteSQL;
    }

    public void ajouterRequêteSQL(String requêteSQL) {
        this.requêteSQL = requêteSQL;
    }

    public Object[] valeurs() {
        return valeurs;
    }

    public void ajouterValeurs(Object... valeurs) {
        this.valeurs = valeurs;
    }

    private String requêteSQL;
    private Object[] valeurs;
}
