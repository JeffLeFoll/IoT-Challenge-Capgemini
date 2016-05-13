package iot.challenge.application.requete.sql;

import java.sql.ResultSet;
import java.util.Optional;

@FunctionalInterface
public interface ProcesseurDeResultSet<TypeRéponse> {

    Optional<TypeRéponse> process(ResultSet rs);
}
