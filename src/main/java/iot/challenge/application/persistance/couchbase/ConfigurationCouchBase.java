package iot.challenge.application.persistance.couchbase;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.CouchbaseCluster;
import com.google.common.base.Strings;

import javax.inject.Inject;
import javax.inject.Named;

public class ConfigurationCouchBase {

    public Bucket bucketCouchBase(){
        Cluster cluster = CouchbaseCluster.create(host);

        if(avecAuthentificationBDD()){
            return cluster.openBucket(nomBucket, motDePasse);
        }

        return cluster.openBucket(nomBucket);
    }

    private boolean avecAuthentificationBDD() {
        return !Strings.isNullOrEmpty(motDePasse);
    }

    @Inject
    @Named("db.host")
    public String host;

    @Inject
    @Named("db.nom")
    public String nomBucket;

    @Inject
    @Named("db.motDePasse")
    public String motDePasse;

}
