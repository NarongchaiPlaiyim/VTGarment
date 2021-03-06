package com.vtgarment.beans;

import com.vtgarment.model.view.rework.ReworkView;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "testFilterBean")
public class TestFilterBean extends Bean{

    private ReworkView reworkView;
    private String testfil;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation()");
        init();
    }

    private List<ReworkView> reworkTableViewList;
    private List<ReworkView> filter;

    private void init(){
//        reworkView = new ReworkView();
//        List<ReworkView> reworkTableViewList = new ArrayList<>();
//        for (int i = 1; i < 23; i++) {
//            ReworkView reworkTableView = new ReworkView();
//            reworkTableView.setSutureLine("VSEW00"+String.valueOf(i));
//            int percentOfYesterday = randInt(0, 100);
//            reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
//            int percentOfToday = randInt(0, 100);
//            reworkTableView.setPercentOfToday(percentOfToday+"%");
//            reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
//            reworkTableViewList.add(reworkTableView);
//        }
//        this.reworkTableViewList = reworkTableViewList;
    }

    public int randInt(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }
}
