package com.vtgarment.beans;

import com.vtgarment.model.view.rework.ReworkTableView;
import com.vtgarment.model.view.rework.ReworkView;
import com.vtgarment.service.ReworkService;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "reworkBean")
public class ReworkBean extends Bean {
    @ManagedProperty("#{reworkService}")
    private ReworkService reworkService;

    private ReworkView reworkView;
    private List<ReworkTableView> reworkTableViewList;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation()");
        init();
    }

    private void init(){
        log.debug("init()");
        reworkView = new ReworkView();
        reworkTableViewList = new ArrayList<>();
        for (int i = 1; i < 23; i++) {
            ReworkTableView reworkTableView = new ReworkTableView();
            reworkTableView.setSutureLine("VSEW00"+String.valueOf(i));
            int percentOfYesterday = randInt(0, 100);
            reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
            int percentOfToday = randInt(0, 100);
            reworkTableView.setPercentOfToday(percentOfToday+"%");
            reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
            reworkTableViewList.add(reworkTableView);
        }
        log.debug("reworkTableViewList {}", reworkTableViewList.size());
    }

    public int randInt(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }
}
