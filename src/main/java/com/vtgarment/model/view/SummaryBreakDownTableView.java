package com.vtgarment.model.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
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
}
