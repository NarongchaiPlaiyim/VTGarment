package com.vtgarment.beans;

import com.vtgarment.service.BreakDownService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "breakDownBean")
public class BreakDownBean extends Bean {
    @ManagedProperty("#{breakDownService}")
    private BreakDownService breakDownService;


}
