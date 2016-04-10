package com.vtgarment.model.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by pakorn on 01/04/2016.
 */
@Getter
@Setter
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

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SummaryTableView{");
        sb.append("totalYesterDay=").append(totalYesterDay);
        sb.append(", totalToDay=").append(totalToDay);
        sb.append(", totalTrend=").append(totalTrend);
        sb.append(", styleTotalYesterDay='").append(styleTotalYesterDay).append('\'');
        sb.append(", styleTotalToDay='").append(styleTotalToDay).append('\'');
        sb.append(", imageTrend='").append(imageTrend).append('\'');
        sb.append(", totalYesterDayOutStading=").append(totalYesterDayOutStading);
        sb.append(", totalToDayOutStading=").append(totalToDayOutStading);
        sb.append(", totalTrendOutStading=").append(totalTrendOutStading);
        sb.append('}');
        return sb.toString();
    }
}
