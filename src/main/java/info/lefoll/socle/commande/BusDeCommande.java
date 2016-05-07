package info.lefoll.socle.commande;

import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.Map;
import java.util.Set;

public class BusDeCommande {

    @Inject
    public BusDeCommande(Set<ManipulateurDeCommande> ensembleDesManipulateurs) {

        ensembleDesManipulateurs.forEach(manipulateur
                -> catalogueDeCommandes.put(manipulateur.getTypeCommande(), manipulateur));
    }

    public void traiterCommande(Commande commande) {
        ManipulateurDeCommande manipulateur = catalogueDeCommandes.get(commande.getClass());

        manipulateur.exécuter(commande);
    }

    private Map<Class<?>, ManipulateurDeCommande> catalogueDeCommandes = Maps.newHashMap();

    private static Logger LOGGER = LoggerFactory.getLogger(BusDeCommande.class);
}
