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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BreakDownTableView{");
        sb.append("lineCode='").append(lineCode).append('\'');
        sb.append(", toDayPeople=").append(toDayPeople);
        sb.append(", stylePeopleToday='").append(stylePeopleToday).append('\'');
        sb.append(", toDayMach=").append(toDayMach);
        sb.append(", styleMachToday='").append(styleMachToday).append('\'');
        sb.append(", toDayMethod=").append(toDayMethod);
        sb.append(", styleMethodToday='").append(styleMethodToday).append('\'');
        sb.append(", toDayMaterial=").append(toDayMaterial);
        sb.append(", styleMaterialToday='").append(styleMaterialToday).append('\'');
        sb.append(", toDayTotal=").append(toDayTotal);
        sb.append(", styleTotalToDay='").append(styleTotalToDay).append('\'');
        sb.append(", yesterDayPeople=").append(yesterDayPeople);
        sb.append(", stylePeopleYesterday='").append(stylePeopleYesterday).append('\'');
        sb.append(", yesterDayMach=").append(yesterDayMach);
        sb.append(", styleMachYesterday='").append(styleMachYesterday).append('\'');
        sb.append(", yesterDayMethod=").append(yesterDayMethod);
        sb.append(", styleMethodYesterday='").append(styleMethodYesterday).append('\'');
        sb.append(", yesterDayMaterial=").append(yesterDayMaterial);
        sb.append(", styleMaterialYesterday='").append(styleMaterialYesterday).append('\'');
        sb.append(", yesterDayTotal=").append(yesterDayTotal);
        sb.append(", styleTotalYesterday='").append(styleTotalYesterday).append('\'');
        sb.append(", trend=").append(trend);
        sb.append(", image='").append(image).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
