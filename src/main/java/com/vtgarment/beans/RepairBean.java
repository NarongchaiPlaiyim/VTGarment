package com.vtgarment.beans;

import com.vtgarment.service.RepairService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "repairBean")
public class RepairBean extends Bean {
    @ManagedProperty("#{repairService}")
    private RepairService repairService;
}
