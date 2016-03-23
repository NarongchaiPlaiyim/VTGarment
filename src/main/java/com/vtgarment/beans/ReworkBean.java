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

    @ManagedProperty("#{centralBean}")
    private CentralBean centralBean;


    private ReworkView reworkView;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation()");
        init();
    }

    private void init(){
        log.debug("init()");
        reworkView = new ReworkView();
        reworkTableViewList = new ArrayList<>();

        ReworkTableView reworkTableView = new ReworkTableView();
        int percentOfYesterday = randInt(0, 100);
        int percentOfToday = randInt(0, 100);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("TH");
        reworkTableView.setFactory("F1");
        reworkTableView.setLine("L1");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("TH");
        reworkTableView.setFactory("F1");
        reworkTableView.setLine("L2");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("TH");
        reworkTableView.setFactory("F1");
        reworkTableView.setLine("L3");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("TH");
        reworkTableView.setFactory("F2");
        reworkTableView.setLine("L1");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("TH");
        reworkTableView.setFactory("F2");
        reworkTableView.setLine("L2");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("TH");
        reworkTableView.setFactory("F2");
        reworkTableView.setLine("L3");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("TH");
        reworkTableView.setFactory("F3");
        reworkTableView.setLine("L1");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("TH");
        reworkTableView.setFactory("F3");
        reworkTableView.setLine("L2");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("TH");
        reworkTableView.setFactory("F3");
        reworkTableView.setLine("L3");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);












        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("LO");
        reworkTableView.setFactory("F1");
        reworkTableView.setLine("L1");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("LO");
        reworkTableView.setFactory("F1");
        reworkTableView.setLine("L2");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("LO");
        reworkTableView.setFactory("F1");
        reworkTableView.setLine("L3");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("LO");
        reworkTableView.setFactory("F2");
        reworkTableView.setLine("L1");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("LO");
        reworkTableView.setFactory("F2");
        reworkTableView.setLine("L2");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("LO");
        reworkTableView.setFactory("F2");
        reworkTableView.setLine("L3");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("LO");
        reworkTableView.setFactory("F3");
        reworkTableView.setLine("L1");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("LO");
        reworkTableView.setFactory("F3");
        reworkTableView.setLine("L2");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);

        reworkTableView = new ReworkTableView();
        reworkTableView.setCountry("LO");
        reworkTableView.setFactory("F3");
        reworkTableView.setLine("L3");
        reworkTableView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkTableView.setPercentOfToday(percentOfToday+"%");
        reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkTableView);
//        for (int i = 1; i < 23; i++) {
//            reworkTableView = new ReworkTableView();
//            reworkTableView.setSutureLine("VSEW00"+String.valueOf(i));
//            int percentOfYesterday = randInt(0, 100);
//            reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
//            int percentOfToday = randInt(0, 100);
//            reworkTableView.setPercentOfToday(percentOfToday+"%");
//            reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
//            reworkTableViewList.add(reworkTableView);
//        }
    }

    public int randInt(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }

    private List<ReworkTableView> reworkTableViewList;
    private List<ReworkTableView> filteredReworkTableViewList;

    public void update() {
        log.debug("update");
//        if (reworkTableViewList == null) {
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
//        } else {
//        this.reworkTableViewList = new ArrayList<>();
//            List<ReworkTableView> reworkTableViewList = centralBean.getReworkTableViewList();
//            if (reworkTableViewList != null) {
//                this.reworkTableViewList = reworkTableViewList;
//            }
//        }
    }
}
