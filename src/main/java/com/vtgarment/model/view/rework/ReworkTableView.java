package com.vtgarment.model.view.rework;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ReworkTableView {
    private String lineCode;
    private BigDecimal toDay;
    private String styleToDay;
    private BigDecimal yesterDay;
    private String styleYesterDay;
    private BigDecimal trend;
    private String image;
}
