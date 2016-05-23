package iot.challenge.application.modele;

import iot.challenge.application.modele.swagger.Synthesis;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SynthèseGénérée extends Synthesis {

    private BigDecimal mediumValue = null;

    @Override
    public BigDecimal getMediumValue() {

        return mediumValue.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void setMediumValue(BigDecimal mediumValue) {
        this.mediumValue = mediumValue;
    }
}
