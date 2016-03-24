package com.vtgarment.model.view.breakdown;

import com.vtgarment.model.view.View;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BreakDownView extends View {
    private String country;
    private String factory;
    private String line;
    private String sutureLine;

    private String personPercentOfYesterday;
    private String machPercentOfYesterday;
    private String methodPercentOfYesterday;
    private String materialPercentOfYesterday;
    private String totalPercentOfYesterday;

    private String personPercentOfToday;
    private String machPercentOfToday;
    private String methodPercentOfToday;
    private String materialPercentOfToday;
    private String totalPercentOfToday;

    private String trends;
}
