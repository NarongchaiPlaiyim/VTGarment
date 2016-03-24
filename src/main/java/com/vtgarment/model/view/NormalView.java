package com.vtgarment.model.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public abstract class NormalView extends View{
    public String country;
    public String factory;
    public String line;
    public String sutureLine;
    public String percentOfYesterday;
    public String percentOfToday;
    public String trends;
}
