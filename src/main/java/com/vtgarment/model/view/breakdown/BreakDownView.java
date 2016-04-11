package com.vtgarment.model.view.breakdown;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class BreakDownView {

    private String bestLineCode;
    private String worstLineCode;
    private BigDecimal trendActual;
    private BigDecimal bestActual;
    private BigDecimal worstActual;

    private BigDecimal trendTarget;
    private BigDecimal bestTarget;
    private BigDecimal worstTarget;

    private String image;
    private String styleTrend;
    private String styleBest;
    private String styleWorst;
}
