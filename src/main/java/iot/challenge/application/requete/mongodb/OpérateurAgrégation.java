package iot.challenge.application.requete.mongodb;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class OpérateurAgrégation {

    public void setTypeEtape(String typeEtape) {
        this.typeEtape = typeEtape;
    }

    public void ajouterExpressionEtape(String expressionEtape) {
        this.expressionsEtape.add(expressionEtape);
    }

    public void ajouterLesParamètres(Object... paramètres) {
        this.paramètres = Optional.ofNullable(paramètres);
    }

    public Object[] getParamètres() {
        return paramètres.orElse(new Object[]{});
    }

    public void ajouterAccumulateur(Accumulateur accumulateur) {
        accumulateurs.add(accumulateur);
    }

    public String getEtape() {

        StringBuilder expressionsEtAccumulateurs = new StringBuilder("");
        expressionsEtAccumulateurs.append(formaterExpressionsEtape());
        expressionsEtAccumulateurs.append(formaterAccumulateurs());

        if(Strings.isNullOrEmpty(expressionsEtAccumulateurs.toString())) {
            return "";
        }

        return String.format("{%s: {%s}}", typeEtape, expressionsEtAccumulateurs);
    }

    public boolean estDeType(String typeOpérateur) {
        return typeEtape.equalsIgnoreCase(typeOpérateur);
    }

    private String formaterExpressionsEtape() {
        StringBuilder builderExpressions = new StringBuilder("");

        if(!expressionsEtape.isEmpty()) {
            expressionsEtape.forEach(expression -> builderExpressions.append(expression).append(", "));

            builderExpressions.delete(builderExpressions.length() - 2, builderExpressions.length());
        }

        return builderExpressions.toString();
    }

    private String formaterAccumulateurs() {
        StringBuilder builderAccumulateur = new StringBuilder("");

        if (!accumulateurs.isEmpty()) {
            builderAccumulateur.append(",");

            accumulateurs.forEach(accumulateur -> builderAccumulateur.append(" ").append(accumulateur.getExpression()));

        }
        return builderAccumulateur.toString();
    }

    private String typeEtape;
    private List<String> expressionsEtape = Lists.newArrayList();
    private List<Accumulateur> accumulateurs = Lists.newArrayList();
    private Optional<Object[]> paramètres = Optional.empty();


    public static class Accumulateur {

        public Accumulateur(String expression) {
            this.expression = expression;
        }

        public String getExpression() {

            return Strings.nullToEmpty(expression);
        }

        private String expression;
    }
}
