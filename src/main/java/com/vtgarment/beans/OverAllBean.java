package com.vtgarment.beans;

import com.vtgarment.model.ViewTypeValue;
import com.vtgarment.utils.FacesUtil;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "overAllBean")
public class OverAllBean extends Bean
{
    @PostConstruct
    public void onCreation(){
        log.debug("onCreation()");
        init();
    }

    private void init(){

    }

    public void onClickViewDetail(String view){
        log.debug("onClickViewDetail : {}", view);
        if (ViewTypeValue.OTP.getName().equals(view)){
            FacesUtil.redirect("/site/otp.xhtml");
        } else if (ViewTypeValue.REWORK.getName().equals(view)){
            FacesUtil.redirect("/site/rework.xhtml");
        } else if (ViewTypeValue.BREAK_DOWN.getName().equals(view)){
            FacesUtil.redirect("/site/breakDown.xhtml");
        } else if (ViewTypeValue.OUTSTANDING.getName().equals(view)){
            FacesUtil.redirect("/site/outstading.xhtml");
        }
    }
}
