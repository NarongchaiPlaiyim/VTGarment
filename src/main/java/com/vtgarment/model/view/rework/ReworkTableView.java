package com.vtgarment.model.view.rework;

import com.vtgarment.model.view.View;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReworkTableView extends View {
    private String country;
    private String factory;
    private String line;
    private String sutureLine;
    private String percentOfYesterday;
    private String percentOfToday;
    private String trends;
}
