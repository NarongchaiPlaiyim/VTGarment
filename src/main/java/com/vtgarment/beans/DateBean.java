package com.vtgarment.beans;

import com.vtgarment.utils.DateUtil;
import com.vtgarment.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "dateBean")
public class DateBean {
    private String previousDate;
    private String currentDate;

    @PostConstruct
    public void DateBean() {
        previousDate = DateUtil.convertDateToString_DD_MM_YYYY(Utils.previousDate());
        currentDate = DateUtil.convertDateToString_DD_MM_YYYY(Utils.currentDate());
    }
}
