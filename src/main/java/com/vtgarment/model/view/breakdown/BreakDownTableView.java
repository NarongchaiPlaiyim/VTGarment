package com.vtgarment.model.view.breakdown;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by pakorn on 03/04/2016.
 */
@Getter
@Setter
public class BreakDownTableView {
    private String lineCode;

    private BigDecimal toDayPeople;
    private BigDecimal toDayMach;
    private BigDecimal toDayMethod;
    private BigDecimal toDayRawMaterial;
    private BigDecimal toDayTrend;

    private BigDecimal yesterDayPeople;
    private BigDecimal yesterDayMach;
    private BigDecimal yesterDayMethod;
    private BigDecimal yesterDayRawMaterial;
    private BigDecimal yesterDayTrend;

    private BigDecimal trend;
    private String image;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BreakDownTableView{");
        sb.append("lineCode='").append(lineCode).append('\'');
        sb.append(", toDayPeople=").append(toDayPeople);
        sb.append(", toDayMach=").append(toDayMach);
        sb.append(", toDayMethod=").append(toDayMethod);
        sb.append(", toDayRawMaterial=").append(toDayRawMaterial);
        sb.append(", toDayTrend=").append(toDayTrend);
        sb.append(", yesterDayPeople=").append(yesterDayPeople);
        sb.append(", yesterDayMach=").append(yesterDayMach);
        sb.append(", yesterDayMethod=").append(yesterDayMethod);
        sb.append(", yesterDayRawMaterial=").append(yesterDayRawMaterial);
        sb.append(", yesterDayTrend=").append(yesterDayTrend);
        sb.append(", trend=").append(trend);
        sb.append(", image='").append(image).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
