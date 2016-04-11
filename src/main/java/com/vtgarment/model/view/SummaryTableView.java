package com.vtgarment.model.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class SummaryTableView {

    private BigDecimal totalYesterDay = BigDecimal.ZERO;
    private BigDecimal totalToDay = BigDecimal.ZERO;
    private BigDecimal totalTrend = BigDecimal.ZERO;
    private String styleTotalYesterDay;
    private String styleTotalToDay;
    private String imageTrend;

    private int totalYesterDayOutStading;
    private int totalToDayOutStading;
    private int totalTrendOutStading;
}
