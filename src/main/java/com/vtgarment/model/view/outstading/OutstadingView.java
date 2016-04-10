package com.vtgarment.model.view.outstading;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by pakorn on 31/03/2016.
 */
@Getter
@Setter
public class OutstadingView {
    private String bestLineCode;
    private String worstLineCode;
    private int trendActual;
    private int bestActual;
    private int worstActual;

    private int trendTarget;
    private int bestTarget;
    private int worstTarget;

    private String image;
    private String styleTrend;
    private String styleBest;
    private String styleWorst;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OTPView{");
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
