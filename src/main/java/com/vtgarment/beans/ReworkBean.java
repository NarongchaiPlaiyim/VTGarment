package com.vtgarment.beans;

import com.vtgarment.service.ReworkService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "reworkBean")
public class ReworkBean extends Bean {
    @ManagedProperty("#{reworkService}")
    private ReworkService reworkService;
}
