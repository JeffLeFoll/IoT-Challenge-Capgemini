package iot.challenge.application.requete.mongodb;

public class ConstructeurDOpérateur {

    public ConstructeurDOpérateur() {
        opérateurAgrégation = new OpérateurAgrégation();
    }

    public ConstructeurDOpérateur avecLeTypeEtape(String typeEtape) {
        opérateurAgrégation.setTypeEtape(typeEtape);

        return this;
    }

    public ConstructeurDOpérateur avecLExpressionEtape(String expressionEtape) {
        opérateurAgrégation.ajouterExpressionEtape(expressionEtape);

        return this;
    }

    public ConstructeurDOpérateur avecLesParamètres(Object... paramètres){
        opérateurAgrégation.ajouterLesParamètres(paramètres);

        return this;
    }

    public ConstructeurDOpérateur avecLAccumulateur(OpérateurAgrégation.Accumulateur accumulateur) {
        opérateurAgrégation.ajouterAccumulateur(accumulateur);

        return this;
    }

    public OpérateurAgrégation construire(){
        return opérateurAgrégation;
    }

    private OpérateurAgrégation opérateurAgrégation;
}
