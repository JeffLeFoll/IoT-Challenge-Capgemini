package iot.challenge.application.modele;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;


@javax.annotation.Generated(value = "class io.swagger.codegen.languages.JavaJerseyServerCodegen", date = "2016-05-07T05:56:16.626Z")
public class Synthesis {

    private Integer sensorType = null;
    private Long minValue = null;
    private Long maxValue = null;
    private Long mediumValue = null;


    /**
     * Type de capteur \u00E0 l'origine de l'emission du message.
     **/
    public Synthesis sensorType(Integer sensorType) {
        this.sensorType = sensorType;
        return this;
    }


    @JsonProperty("sensorType")
    public Integer getSensorType() {
        return sensorType;
    }

    public void setSensorType(Integer sensorType) {
        this.sensorType = sensorType;
    }


    /**
     * Valeur minimum transmise par ce type de capteur.
     **/
    public Synthesis minValue(Long minValue) {
        this.minValue = minValue;
        return this;
    }


    @JsonProperty("minValue")
    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }


    /**
     * Valeur maximale transmise par ce capteur type de capteur.
     **/
    public Synthesis maxValue(Long maxValue) {
        this.maxValue = maxValue;
        return this;
    }


    @JsonProperty("maxValue")
    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }


    /**
     * Valeur moyenne des donn\u00E9es transmises par ce type de capteur arrondie \u00E0 deux d\u00E9cimales
     **/
    public Synthesis mediumValue(Long mediumValue) {
        this.mediumValue = mediumValue;
        return this;
    }


    @JsonProperty("mediumValue")
    public Long getMediumValue() {
        return mediumValue;
    }

    public void setMediumValue(Long mediumValue) {
        this.mediumValue = mediumValue;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Synthesis synthesis = (Synthesis) o;
        return Objects.equals(sensorType, synthesis.sensorType) &&
                Objects.equals(minValue, synthesis.minValue) &&
                Objects.equals(maxValue, synthesis.maxValue) &&
                Objects.equals(mediumValue, synthesis.mediumValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sensorType, minValue, maxValue, mediumValue);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Synthesis {\n");

        sb.append("    sensorType: ").append(toIndentedString(sensorType)).append("\n");
        sb.append("    minValue: ").append(toIndentedString(minValue)).append("\n");
        sb.append("    maxValue: ").append(toIndentedString(maxValue)).append("\n");
        sb.append("    mediumValue: ").append(toIndentedString(mediumValue)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

