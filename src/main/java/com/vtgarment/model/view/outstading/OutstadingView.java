package com.vtgarment.model.view.outstading;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
}
