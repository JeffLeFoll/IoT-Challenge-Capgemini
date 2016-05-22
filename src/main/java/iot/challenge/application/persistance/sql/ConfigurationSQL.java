package iot.challenge.application.persistance.sql;

import com.google.common.base.Strings;
import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConfigurationSQL {

    public Statement fabriquerStatementSQLite() throws SQLException {

        Connection connection = fabriquerConnection();

        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);

        return statement;
    }

    private Connection fabriquerConnection() throws SQLException {

        DriverManager.registerDriver(new org.sqlite.JDBC());

        String url = String.format("jdbc:sqlite:%s", chemin);
        LOGGER.info(String.format("Chargement de la base %s avec l'url %s", chemin, url));

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

    private static Logger LOGGER = LoggerFactory.getLogger(ConfigurationSQL.class);
}
