package iot.challenge.application.persistance.sqlite;

import com.google.common.base.Strings;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConfigurationSQLite {

    public Statement fabriquerStatementSQLite() throws SQLException {

        Connection connection = fabriquerConnection();

        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        return statement;
    }

    private Connection fabriquerConnection() throws SQLException {

        DriverManager.registerDriver(new org.sqlite.JDBC());
        String url = String.format("jdbc:sqlite:%s", chemin);

        if(avecAuthentificationBDD()){
            return DriverManager.getConnection(url, utilisateur, motDePasse);
        }

        return DriverManager.getConnection(url);
    }

    private boolean avecAuthentificationBDD() {
        return !Strings.isNullOrEmpty(utilisateur);
    }

    @Inject
    @Named("db.path")
    public String chemin;

    @Inject
    @Named("db.utilisateur")
    public String utilisateur;

    @Inject
    @Named("db.motDePasse")
    public String motDePasse;

}
