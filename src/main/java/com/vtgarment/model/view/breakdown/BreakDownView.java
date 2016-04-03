package com.vtgarment.model.view.breakdown;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by pakorn on 01/04/2016.
 */
@Getter
@Setter
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ReworkView{");
        sb.append("bestLineCode='").append(bestLineCode).append('\'');
        sb.append(", worstLineCode='").append(worstLineCode).append('\'');
        sb.append(", trendActual=").append(trendActual);
        sb.append(", bestActual=").append(bestActual);
        sb.append(", worstActual=").append(worstActual);
        sb.append(", trendTarget=").append(trendTarget);
        sb.append(", bestTarget=").append(bestTarget);
        sb.append(", worstTarget=").append(worstTarget);
        sb.append(", image='").append(image).append('\'');
        sb.append(", styleTrend='").append(styleTrend).append('\'');
        sb.append(", styleBest='").append(styleBest).append('\'');
        sb.append(", styleWorst='").append(styleWorst).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
