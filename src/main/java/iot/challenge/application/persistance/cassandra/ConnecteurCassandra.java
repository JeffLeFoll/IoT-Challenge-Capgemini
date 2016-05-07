package iot.challenge.application.persistance.cassandra;


import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import info.lefoll.socle.persistance.Connecteur;
import iot.challenge.application.requete.cassandra.AgrégationCassandra;

import javax.inject.Inject;
import java.util.Optional;

public class ConnecteurCassandra implements Connecteur {

    @Inject
    public ConnecteurCassandra(Session session, MappingManager mappingManager){
        this.session = session;
        this.mappingManager = mappingManager;
    }

    @Override
    public <T> void pourLEntité(Class<T> typeDeLEntité){
        this.typeDeLEntité = typeDeLEntité;
    }

    @Override
    public <T> void créerEntité(T donnée) {

        mapperDeDonnée().save(donnée);
    }

    @Override
    public <T> Optional<T> rechercherEntitéParId(String id) {

        T entité = (T) mapperDeDonnée().get(id);

        return Optional.ofNullable(entité);
    }

    public Optional<ResultSet> exécuterRequete(AgrégationCassandra agrégation) {

        PreparedStatement preparedStatement = session.prepare(agrégation.requêteSQL());

        BoundStatement boundStatement = preparedStatement.bind(agrégation.valeurs());

        ResultSet résultat = session.execute(boundStatement);

        return Optional.ofNullable(résultat);
    }

    private <T> Mapper<T> mapperDeDonnée(){
        return mappingManager.mapper((Class<T>) typeDeLEntité);
    }

    private Session session;
    private MappingManager mappingManager;
    private Class<?> typeDeLEntité;

}
