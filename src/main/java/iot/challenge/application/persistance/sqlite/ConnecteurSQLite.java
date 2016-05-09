package iot.challenge.application.persistance.sqlite;

import info.lefoll.socle.persistance.Connecteur;
import info.lefoll.socle.requete.Agrégation;

import javax.inject.Inject;
import java.sql.Statement;
import java.util.Optional;

public class ConnecteurSQLite implements Connecteur {

    @Inject
    public ConnecteurSQLite(Statement statementSQLite){
        this.statementSQLite = statementSQLite;
    }

    @Override
    public <T> void créerEntité(T donnée) {

    }

    @Override
    public <T> Optional<T> rechercherEntitéParId(String id) {

        //statementSQLite.executeQuery("select * from person");
        return null;
    }

    @Override
    public <T> Optional<T> effectuerRequête(Agrégation agrégation) {
        return null;
    }

    @Override
    public <T> void pourLEntité(Class<T> typeDeLEntité) {
        this.typeDeLEntité = typeDeLEntité;
    }

    private Statement statementSQLite;
    private Class<?> typeDeLEntité;
}
