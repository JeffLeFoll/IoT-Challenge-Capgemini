package iot.challenge.application.requete.mongodb;

import info.lefoll.socle.requete.Agrégation;

import java.util.Optional;

public class AgrégationMongoDB implements Agrégation {

    public void setMatch(OpérateurAgrégation match) {
        this.match = Optional.ofNullable(match);
    }

    public OpérateurAgrégation getMatch() {
        return match.orElse(new OpérateurAgrégation());
    }

    public void setGroup(OpérateurAgrégation group) {
        this.group = Optional.ofNullable(group);
    }

    public OpérateurAgrégation getGroup() {
        return group.orElse(new OpérateurAgrégation());
    }

    private Optional<OpérateurAgrégation> match = Optional.empty();
    private Optional<OpérateurAgrégation> group= Optional.empty();
}
