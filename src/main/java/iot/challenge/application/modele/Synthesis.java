package iot.challenge.application.modele;

import org.jongo.marshall.jackson.oid.MongoId;

import java.util.Objects;

public class Synthesis {

    @MongoId
    private Integer sensorType = null;
    private Long minValue = null;
    private Long maxValue = null;
    private Long mediumValue = null;

    public Synthesis sensorType(Integer sensorType) {
        this.sensorType = sensorType;
        return this;
    }

    public Integer getSensorType() {
        return sensorType;
    }

    public void setSensorType(Integer sensorType) {
        this.sensorType = sensorType;
    }

    public Synthesis minValue(Long minValue) {
        this.minValue = minValue;
        return this;
    }

    public Long getMinValue() {
        return minValue;
    }

    public void setMinValue(Long minValue) {
        this.minValue = minValue;
    }

    public Synthesis maxValue(Long maxValue) {
        this.maxValue = maxValue;
        return this;
    }

    public Long getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Long maxValue) {
        this.maxValue = maxValue;
    }

    public Synthesis mediumValue(Long mediumValue) {
        this.mediumValue = mediumValue;
        return this;
    }

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

    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

