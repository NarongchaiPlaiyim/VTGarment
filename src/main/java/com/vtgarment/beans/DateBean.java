package com.vtgarment.beans;

import com.vtgarment.utils.DateUtil;
import com.vtgarment.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@Getter
@Setter
@ApplicationScoped
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
