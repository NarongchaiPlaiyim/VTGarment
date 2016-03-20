package com.vtgarment.beans;

import com.vtgarment.service.OtpService;
import lombok.Getter;
import lombok.Setter;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "otpBean")
public class OtpBean extends Bean {
    @ManagedProperty("#{otpService}")
    private OtpService otpService;
}
