package com.vtgarment.model.view;

import org.slf4j.Logger;

import javax.annotation.Resource;
import java.io.Serializable;

public abstract class View implements Serializable {
    @Resource
    protected Logger log;
//    public String sutureLine;
//    public String percentOfYesterday;
//    public String percentOfToday;
//    public String trends;
}
