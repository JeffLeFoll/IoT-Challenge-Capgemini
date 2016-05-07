package info.lefoll.socle.requete;

import com.google.common.reflect.TypeToken;

public interface ManipulateurDeRequête<TypeRequête extends Requête, TypeRéponse> {

    TypeRéponse exécuter(TypeRequête requête);

    default Class<TypeRequête> getTypeRequête() {
        TypeToken<TypeRequête> type = new TypeToken<TypeRequête>(getClass()) {
        };

        return (Class<TypeRequête>) type.getRawType();
    }
}
