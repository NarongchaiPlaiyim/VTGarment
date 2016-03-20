package com.vtgarment.beans;

import com.vtgarment.service.MainService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "mainBean")
public class MainBean extends Bean {
    @ManagedProperty("#{mainService}")
    private MainService mainService;
}
