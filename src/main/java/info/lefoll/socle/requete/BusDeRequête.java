package info.lefoll.socle.requete;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;

public class BusDeRequête {

    @Inject
    public BusDeRequête(Set<ManipulateurDeRequête> ensembleDesManipulateurs) {

        ensembleDesManipulateurs.forEach(manipulateur
                -> catalogueDeRequêtes.put(manipulateur.getTypeRequête(), manipulateur));
    }

    public Object traiterRequête(Requête requête) {
        ManipulateurDeRequête manipulateur = catalogueDeRequêtes.get(requête.getClass());

        return manipulateur.exécuter(requête);
    }

    private Map<Class<?>, ManipulateurDeRequête> catalogueDeRequêtes = Maps.newHashMap();

    private static Logger LOGGER = LoggerFactory.getLogger(BusDeRequête.class);
}
