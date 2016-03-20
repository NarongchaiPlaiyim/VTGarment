package com.vtgarment.beans;

import com.vtgarment.service.LoginService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "index")
public class IndexBean extends Bean {
    private static final long serialVersionUID = 4112578634029874840L;
    @ManagedProperty("#{loginService}") private LoginService loginService;
    @ManagedProperty("#{msg}") private MessageBean msg;


    private String messageIndex;
    private List<String> root;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation().");
//        if(preLoad()){
            init();
//        }
    }

    private void init(){
        messageIndex = "HELLO!!!!!!!!!";
        root = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            root.add("a" + i);
        }
    }



}
