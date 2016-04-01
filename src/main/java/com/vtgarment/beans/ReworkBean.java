package com.vtgarment.beans;

import com.sun.istack.internal.NotNull;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.model.view.rework.ReworkView;
import com.vtgarment.service.OutstadingService;
import com.vtgarment.utils.FacesUtil;
import com.vtgarment.utils.Utils;
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
    @ManagedProperty("#{outstadingService}") private OutstadingService reworkService;

    @NotNull
    private List<FactoryModel> factoryModelList;
    @NotNull private List<BuildingFloorModel> buildingFloorModelList;
    @NotNull private List<LineModel> lineModelList;

    @NotNull private int factoryId;
    @NotNull private int buildingFloorId;
    @NotNull private int lineId;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation()");
        init();
    }

    private void init(){
        factory();
    }

    private void factory(){
        log.debug("factory");
        factoryId = 0;
        factoryModelList = reworkService.findAll();
    }

    public void filterBuildingFloor(){
        log.debug("filterBuildingFloor : {}", factoryId);
        buildingFloorId = 0;
        buildingFloorModelList = reworkService.findBuildingFloorByFactoryId(factoryId);
    }

    public void filterLine(){
        log.debug("filterLine : {}", buildingFloorId);
        lineId = 0;
        lineModelList = reworkService.findLineByBuildingFloorId(buildingFloorId);
    }

    public void filterValue(){
        log.debug("Factory : {}, BuildingFloor : {}, Line : {}", factoryId, buildingFloorId, lineId);
    }

    public void onClickBtnBack(){
        if (!Utils.isZero(lineId)){
            lineModelList = new ArrayList<>();
        } else if (!Utils.isZero(buildingFloorId)){
            buildingFloorModelList = new ArrayList<>();
        } else if (!Utils.isZero(factoryId)){
            factoryId = 0;
        } else {
            FacesUtil.redirect("/site/overAll.xhtml");
        }

    }

    public void filterListener(FilterEvent filterEvent) {
        // your code here...
        filterEvent.getFilters();
    }

//    private void init(){
//        log.debug("init()");
//        reworkTableViewList = new ArrayList<>();
//
//        ReworkView reworkView;
//        int percentOfYesterday = randInt(0, 100);
//        int percentOfToday = randInt(0, 100);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("VSEW001");
//        reworkView.setFactory("F1");
//        reworkView.setLine("L1");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("TH");
//        reworkView.setFactory("F1");
//        reworkView.setLine("L2");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("TH");
//        reworkView.setFactory("F1");
//        reworkView.setLine("L3");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("TH");
//        reworkView.setFactory("F2");
//        reworkView.setLine("L1");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("TH");
//        reworkView.setFactory("F2");
//        reworkView.setLine("L2");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("TH");
//        reworkView.setFactory("F2");
//        reworkView.setLine("L3");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("TH");
//        reworkView.setFactory("F3");
//        reworkView.setLine("L1");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("TH");
//        reworkView.setFactory("F3");
//        reworkView.setLine("L2");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("TH");
//        reworkView.setFactory("F3");
//        reworkView.setLine("L3");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//
//
//
//
//
//
//
//
//
//
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("LO");
//        reworkView.setFactory("F1");
//        reworkView.setLine("L1");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("LO");
//        reworkView.setFactory("F1");
//        reworkView.setLine("L2");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("LO");
//        reworkView.setFactory("F1");
//        reworkView.setLine("L3");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("LO");
//        reworkView.setFactory("F2");
//        reworkView.setLine("L1");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("LO");
//        reworkView.setFactory("F2");
//        reworkView.setLine("L2");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("LO");
//        reworkView.setFactory("F2");
//        reworkView.setLine("L3");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("LO");
//        reworkView.setFactory("F3");
//        reworkView.setLine("L1");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("LO");
//        reworkView.setFactory("F3");
//        reworkView.setLine("L2");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
//
//        reworkView = new ReworkView();
//        reworkView.setCountry("LO");
//        reworkView.setFactory("F3");
//        reworkView.setLine("L3");
//        reworkView.setSutureLine("VSEW00"+String.valueOf(3));
//        reworkView.setPercentOfYesterday(percentOfYesterday+"%");
//        reworkView.setPercentOfToday(percentOfToday+"%");
//        reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
//        reworkTableViewList.add(reworkView);
////        for (int i = 1; i < 23; i++) {
////            reworkView = new ReworkView();
////            reworkView.setSutureLine("VSEW00"+String.valueOf(i));
////            int percentOfYesterday = randInt(0, 100);
////            reworkView.setPercentOfYesterday(percentOfYesterday+"%");
////            int percentOfToday = randInt(0, 100);
////            reworkView.setPercentOfToday(percentOfToday+"%");
////            reworkView.setTrends((percentOfYesterday - percentOfToday) +"%");
////            reworkTableViewList.add(reworkView);
////        }
//    }

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
