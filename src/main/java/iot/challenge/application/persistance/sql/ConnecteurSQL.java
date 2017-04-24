package iot.challenge.application.persistance.sql;

import info.lefoll.socle.persistance.Connecteur;
import info.lefoll.socle.requete.Agrégation;
import iot.challenge.application.requete.sql.AgrégationSQL;
import iot.challenge.application.requete.sql.ProcesseurDeResultSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

@SQL
public class ConnecteurSQL implements Connecteur {

    @Inject
    public ConnecteurSQL(Statement statementSQLite){
        this.statementSQLite = statementSQLite;
    }

    @Override
    public <T> void créerEntité(T donnée) {

        assert donnée instanceof AgrégationSQL;
        AgrégationSQL agrégationSQL = ((AgrégationSQL) donnée);

        try (PreparedStatement ps = statementSQLite.getConnection().prepareStatement(agrégationSQL.requêteSQL())) {

            Object[] tableauDeValeurs = agrégationSQL.valeurs();

            for (int index = 0; index < tableauDeValeurs.length; index++) {
                ps.setObject(index + 1, tableauDeValeurs[index]);
            }

            ps.executeUpdate();

        } catch (SQLException e) {
            LOGGER.error("Erreur lors de l'insertion de l'entité: " + donnée, e);
        }
    }

    @Override
    public <T> Optional<T> rechercherEntitéParId(String id) {

        throw new UnsupportedOperationException("Utiliser la fonction effectuerRequête");
    }

    @Override
    public <T> Optional<T> effectuerRequête(Agrégation agrégation) {

        assert agrégation instanceof AgrégationSQL;
        AgrégationSQL agrégationSQL = ((AgrégationSQL) agrégation);

        try (PreparedStatement ps = statementSQLite.getConnection().prepareStatement(agrégationSQL.requêteSQL())) {

            Object[] tableauDeValeurs = agrégationSQL.valeurs();

            for (int index = 0; index < tableauDeValeurs.length; index++) {
                ps.setObject(index + 1, tableauDeValeurs[index]);
            }

            ResultSet résultat = ps.executeQuery();

            return (Optional<T>) processeurDeResultSet.process(résultat);

        } catch (SQLException e) {
            LOGGER.error("Erreur lors de la requête: " + agrégation, e);
        }

        return Optional.empty();
    }

    @Override
    public <T> void pourLEntité(Class<T> typeDeLEntité) {
        this.typeDeLEntité = typeDeLEntité;
    }

    public <T> ConnecteurSQL avecLeProcesseur(ProcesseurDeResultSet<T> processeurDeResultSet){
        this.processeurDeResultSet=processeurDeResultSet;

        return this;
    }

    private Statement statementSQLite;
    private Class<?> typeDeLEntité;
    private ProcesseurDeResultSet<?> processeurDeResultSet;

    private static Logger LOGGER = LoggerFactory.getLogger(ConnecteurSQL.class);
}
