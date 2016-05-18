package iot.challenge.application.injection;

import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import info.lefoll.socle.commande.BusDeCommande;
import info.lefoll.socle.commande.ManipulateurDeCommande;
import info.lefoll.socle.depot.Dépôt;
import info.lefoll.socle.fondation.guice.InjecteurGuiceDynamique;
import info.lefoll.socle.persistance.Connecteur;
import info.lefoll.socle.requete.BusDeRequête;
import info.lefoll.socle.requete.ManipulateurDeRequête;
import iot.challenge.application.persistance.cassandra.Cassandra;
import iot.challenge.application.persistance.cassandra.ConnecteurCassandra;
import iot.challenge.application.persistance.couchbase.ConnecteurCouchBase;
import iot.challenge.application.persistance.couchbase.CouchBase;
import iot.challenge.application.persistance.mongo.ConnecteurMongoAvecJongo;
import iot.challenge.application.persistance.mongo.MongoDB;
import iot.challenge.application.persistance.sql.ConfigurationSQL;
import iot.challenge.application.persistance.sql.ConnecteurSQL;
import iot.challenge.application.persistance.sql.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.Properties;


public class ApplicationModule extends AbstractModule {

    @Override
    protected void configure() {

        Names.bindProperties(binder(), chargerPropriétés());

        configurerPersistance();
        configurerCommandes();
        configurerRequêtes();
        configurerDépôt();
    }

    private Properties chargerPropriétés() {
        URL url = Resources.getResource("env/" + Optional.ofNullable(System.getenv("env")).orElse("dev") + "/configuration.properties");

        ByteSource inputSupplier = Resources.asByteSource(url);

        Properties properties = new Properties();
        try {
            properties.load(inputSupplier.openStream());
        } catch (IOException e) {
            LOGGER.error("Impossible de charger la configuration", e);
        }

        return properties;
    }

    private void configurerPersistance() {


        bind(Connecteur.class).annotatedWith(SQL.class).to(ConnecteurSQL.class);
    }

    private void configurerCommandes() {

        InjecteurGuiceDynamique.listerEtBinderLesTypes(binder(), ManipulateurDeCommande.class, "iot.challenge.application.commande");
        bind(BusDeCommande.class).asEagerSingleton();
    }

    private void configurerRequêtes() {

        InjecteurGuiceDynamique.listerEtBinderLesTypes(binder(), ManipulateurDeRequête.class, "iot.challenge.application.requete");
        bind(BusDeRequête.class).asEagerSingleton();
    }

    private void configurerDépôt() {

        InjecteurGuiceDynamique.listerEtBinderLesTypes(binder(), Dépôt.class, "iot.challenge.application.depot");
    }

    @Provides
    @Singleton
    public Statement sqlite(ConfigurationSQL configurationSQL) throws SQLException {

        return configurationSQL.fabriquerStatementSQLite();
    }

    private static Logger LOGGER = LoggerFactory.getLogger(ApplicationModule.class);
}
