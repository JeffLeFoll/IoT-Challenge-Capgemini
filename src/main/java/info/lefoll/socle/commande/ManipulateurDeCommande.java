package info.lefoll.socle.commande;

import com.google.common.reflect.TypeToken;

public interface ManipulateurDeCommande<TypeCommande extends Commande> {

    void exécuter(TypeCommande commande);

    default Class<TypeCommande> getTypeCommande(){
        TypeToken<TypeCommande> type =  new TypeToken<TypeCommande>(getClass()){};

        return (Class<TypeCommande>) type.getRawType();
    }
}
