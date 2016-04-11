package com.vtgarment.model.view.breakdown;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class BreakDownTableView {
    private String lineCode;

    private BigDecimal toDayPeople;
    private String stylePeopleToday;
    private BigDecimal toDayMach;
    private String styleMachToday;
    private BigDecimal toDayMethod;
    private String styleMethodToday;
    private BigDecimal toDayMaterial;
    private String styleMaterialToday;
    private BigDecimal toDayTotal;
    private String styleTotalToDay;

    private BigDecimal yesterDayPeople;
    private String stylePeopleYesterday;
    private BigDecimal yesterDayMach;
    private String styleMachYesterday;
    private BigDecimal yesterDayMethod;
    private String styleMethodYesterday;
    private BigDecimal yesterDayMaterial;
    private String styleMaterialYesterday;
    private BigDecimal yesterDayTotal;
    private String styleTotalYesterday;

    private BigDecimal trend;
    private String image;
}
