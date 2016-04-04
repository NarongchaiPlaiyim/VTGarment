package com.vtgarment.model.view;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by pakorn on 01/04/2016.
 */
@Getter
@Setter
public class SummaryBreakDownTableView {

    private BigDecimal totalPeopleToDay = BigDecimal.ZERO;
    private BigDecimal totalMachToDay = BigDecimal.ZERO;
    private BigDecimal totalMethodToDay = BigDecimal.ZERO;
    private BigDecimal totalMaterialToDay = BigDecimal.ZERO;
    private BigDecimal totalAllToDay = BigDecimal.ZERO;

    private BigDecimal totalPeopleYesterDay = BigDecimal.ZERO;
    private BigDecimal totalMachYesterDay = BigDecimal.ZERO;
    private BigDecimal totalMethodYesterDay = BigDecimal.ZERO;
    private BigDecimal totalMaterialYesterDay = BigDecimal.ZERO;
    private BigDecimal totalAllYesterDay = BigDecimal.ZERO;

    private String stylePeopleToDay;
    private String styleMachToDay;
    private String styleMethodToDay;
    private String styleMaterialToDay;
    private String styleAllToDay;

    private String stylePeopleYesterDay;
    private String styleMachYesterDay;
    private String styleMethodYesterDay;
    private String styleMaterialYesterDay;
    private String styleAllYesterDay;

    private BigDecimal totalTrend = BigDecimal.ZERO;
    private String imageSummaryTrend;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SummaryBreakDownTableView{");
        sb.append("totalPeopleToDay=").append(totalPeopleToDay);
        sb.append(", totalMachToDay=").append(totalMachToDay);
        sb.append(", totalMethodToDay=").append(totalMethodToDay);
        sb.append(", totalMaterialToDay=").append(totalMaterialToDay);
        sb.append(", totalAllToDay=").append(totalAllToDay);
        sb.append(", totalPeopleYesterDay=").append(totalPeopleYesterDay);
        sb.append(", totalMachYesterDay=").append(totalMachYesterDay);
        sb.append(", totalMethodYesterDay=").append(totalMethodYesterDay);
        sb.append(", totalMaterialYesterDay=").append(totalMaterialYesterDay);
        sb.append(", totalAllYesterDay=").append(totalAllYesterDay);
        sb.append(", stylePeopleToDay='").append(stylePeopleToDay).append('\'');
        sb.append(", styleMachToDay='").append(styleMachToDay).append('\'');
        sb.append(", styleMethodToDay='").append(styleMethodToDay).append('\'');
        sb.append(", styleMaterialToDay='").append(styleMaterialToDay).append('\'');
        sb.append(", styleAllToDay='").append(styleAllToDay).append('\'');
        sb.append(", stylePeopleYesterDay='").append(stylePeopleYesterDay).append('\'');
        sb.append(", styleMachYesterDay='").append(styleMachYesterDay).append('\'');
        sb.append(", styleMethodYesterDay='").append(styleMethodYesterDay).append('\'');
        sb.append(", styleMaterialYesterDay='").append(styleMaterialYesterDay).append('\'');
        sb.append(", styleAllYesterDay='").append(styleAllYesterDay).append('\'');
        sb.append(", totalTrend=").append(totalTrend);
        sb.append(", imageSummaryTrend='").append(imageSummaryTrend).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
