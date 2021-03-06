package iot.challenge.application.injection;

import com.couchbase.client.java.Bucket;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingManager;
import com.google.common.io.ByteSource;
import com.google.common.io.Resources;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Names;
import info.lefoll.socle.commande.BusDeCommande;
import info.lefoll.socle.commande.ManipulateurDeCommande;
import info.lefoll.socle.depot.Dépôt;
import info.lefoll.socle.fondation.guice.InjecteurGuiceDynamique;
import info.lefoll.socle.fondation.guice.InjecteurGuiceDynamiqueAvecAnnotation;
import info.lefoll.socle.persistance.Connecteur;
import info.lefoll.socle.requete.BusDeRequête;
import info.lefoll.socle.requete.ManipulateurDeRequête;
import iot.challenge.application.persistance.cassandra.Cassandra;
import iot.challenge.application.persistance.cassandra.ConfigurationCassandra;
import iot.challenge.application.persistance.cassandra.ConnecteurCassandra;
import iot.challenge.application.persistance.couchbase.ConfigurationCouchBase;
import iot.challenge.application.persistance.couchbase.ConnecteurCouchBase;
import iot.challenge.application.persistance.couchbase.CouchBase;
import iot.challenge.application.persistance.mongo.ConfigurationMongoDb;
import iot.challenge.application.persistance.mongo.ConnecteurMongoAvecJongo;
import iot.challenge.application.persistance.mongo.MongoDB;
import iot.challenge.application.persistance.sql.ConfigurationSQL;
import iot.challenge.application.persistance.sql.ConnecteurSQL;
import iot.challenge.application.persistance.sql.SQL;
import org.jongo.Jongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.io.IOException;
import java.net.URL;
import java.net.UnknownHostException;
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
        URL url = Resources.getResource("env/" + Optional.ofNullable(System.getProperty("env")).orElse("dev") + "/configuration.properties");

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
      InjecteurGuiceDynamiqueAvecAnnotation.listerEtBinderLesTypesAnnotés(binder(), Connecteur.class, "iot.challenge.application.persistance");
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
    public Jongo jongo(ConfigurationMongoDb configurationMongoDb) throws UnknownHostException {
        return configurationMongoDb.clientJongo();
    }

    @Provides
    @Singleton
    public Session cassandra(ConfigurationCassandra configurationCassandra) throws UnknownHostException {
        return configurationCassandra.sessionCassandra();
    }

    @Provides
    @Singleton
    public MappingManager mappingManagerCassandra(Session session){
        return new MappingManager(session);
    }

    @Provides
    @Singleton
    public Bucket couchBase(ConfigurationCouchBase configurationCouchBase) {
        return configurationCouchBase.bucketCouchBase();
    }

    @Provides
    @Singleton
    public Statement sqLite(ConfigurationSQL configurationSQL) throws SQLException {
        return configurationSQL.fabriquerStatementSQLite();
    }

    private static Logger LOGGER = LoggerFactory.getLogger(ApplicationModule.class);
}
