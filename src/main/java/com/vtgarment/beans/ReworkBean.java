package com.vtgarment.beans;

import com.vtgarment.model.view.rework.ReworkView;
import com.vtgarment.service.ReworkService;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.data.FilterEvent;

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

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation()");
        init();
    }

    public void filterListener(FilterEvent filterEvent) {
        // your code here...
        filterEvent.getFilters();
    }

    private void init(){
        log.debug("init()");
        reworkTableViewList = new ArrayList<>();

        ReworkView reworkView;
        int percentOfYesterday = randInt(0, 100);
        int percentOfToday = randInt(0, 100);

        reworkView = new ReworkView();
        reworkView.setCountry("TH");
        reworkView.setFactory("F1");
        reworkView.setLine("L1");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("TH");
        reworkView.setFactory("F1");
        reworkView.setLine("L2");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("TH");
        reworkView.setFactory("F1");
        reworkView.setLine("L3");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("TH");
        reworkView.setFactory("F2");
        reworkView.setLine("L1");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("TH");
        reworkView.setFactory("F2");
        reworkView.setLine("L2");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("TH");
        reworkView.setFactory("F2");
        reworkView.setLine("L3");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("TH");
        reworkView.setFactory("F3");
        reworkView.setLine("L1");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("TH");
        reworkView.setFactory("F3");
        reworkView.setLine("L2");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("TH");
        reworkView.setFactory("F3");
        reworkView.setLine("L3");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);












        reworkView = new ReworkView();
        reworkView.setCountry("LO");
        reworkView.setFactory("F1");
        reworkView.setLine("L1");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("LO");
        reworkView.setFactory("F1");
        reworkView.setLine("L2");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("LO");
        reworkView.setFactory("F1");
        reworkView.setLine("L3");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("LO");
        reworkView.setFactory("F2");
        reworkView.setLine("L1");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("LO");
        reworkView.setFactory("F2");
        reworkView.setLine("L2");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("LO");
        reworkView.setFactory("F2");
        reworkView.setLine("L3");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("LO");
        reworkView.setFactory("F3");
        reworkView.setLine("L1");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("LO");
        reworkView.setFactory("F3");
        reworkView.setLine("L2");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);

        reworkView = new ReworkView();
        reworkView.setCountry("LO");
        reworkView.setFactory("F3");
        reworkView.setLine("L3");
        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
        reworkView.setPercentOfToday(percentOfToday+"%");
        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
        reworkTableViewList.add(reworkView);
//        for (int i = 1; i < 23; i++) {
//            reworkView = new ReworkView();
//            reworkView.setSutureLine("VSEW00"+String.valueOf(i));
//            int percentOfYesterday = randInt(0, 100);
//            reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//            int percentOfToday = randInt(0, 100);
//            reworkView.setPercentOfToday(percentOfToday+"%");
//            reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//            reworkTableViewList.add(reworkView);
//        }
    }

    public int randInt(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }

    private List<ReworkView> reworkTableViewList;
    private List<ReworkView> filteredReworkViewList;

    public void onValueChange(){
        filteredReworkViewList = reworkTableViewList;
        filteredReworkViewList.remove(0);
    }

    public void update() {
        log.debug("update");
//        if (reworkTableViewList == null) {
            reworkTableViewList = new ArrayList<>();
            for (int i = 1; i < 23; i++) {
                ReworkView reworkTableView = new ReworkView();
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
//            List<ReworkView> reworkTableViewList = centralBean.getReworkViewList();
//            if (reworkTableViewList != null) {
//                this.reworkTableViewList = reworkTableViewList;
//            }
//        }
    }
}
