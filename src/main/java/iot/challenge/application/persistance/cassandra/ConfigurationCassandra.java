package iot.challenge.application.persistance.cassandra;

import com.datastax.driver.core.AuthProvider;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PlainTextAuthProvider;
import com.datastax.driver.core.Session;
import com.datastax.driver.extras.codecs.jdk8.InstantCodec;
import com.google.common.base.Strings;

import javax.inject.Inject;
import javax.inject.Named;

public class ConfigurationCassandra {

    public Session sessionCassandra(){
        Cluster cluster = fabriquerClusterCassndra();

        return cluster.connect();
    }

    private Cluster fabriquerClusterCassndra(){

        Cluster cluster = Cluster.builder()
                .withClusterName(nom)
                .addContactPoint(host)
                .withPort(port)
                .withAuthProvider(fabriquerAuthProvider())
                .withoutMetrics()
                .build();

        cluster.getConfiguration().getCodecRegistry()
                .register(InstantCodec.instance);

        return cluster;
    }

    private boolean avecAuthentificationBDD() {
        return !Strings.isNullOrEmpty(utilisateur);
    }

    private AuthProvider fabriquerAuthProvider() {

        if(avecAuthentificationBDD()){
            return new PlainTextAuthProvider(utilisateur, motDePasse);
        }
        return AuthProvider.NONE;
    }

    @Inject
    @Named("db.host")
    public String host;

    @Inject
    @Named("db.nom")
    public String nom;

    @Inject
    @Named("db.utilisateur")
    public String utilisateur;

    @Inject
    @Named("db.motDePasse")
    public String motDePasse;

    @Inject
    @Named("db.port")
    public int port;
}
