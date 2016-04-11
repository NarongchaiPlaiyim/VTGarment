package com.vtgarment.model.view.outstading;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OutStadingTableView {
    private String lineCode;
    private int toDay;
    private String styleToDay;
    private int yesterDay;
    private String styleYesterDay;
    private int trend;
    private String image;
}
