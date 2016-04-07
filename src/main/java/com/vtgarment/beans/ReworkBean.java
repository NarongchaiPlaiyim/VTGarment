package com.vtgarment.beans;

import com.sun.istack.internal.NotNull;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.model.view.SummaryTableView;
import com.vtgarment.model.view.rework.ReworkTableView;
import com.vtgarment.service.ReworkService;
import com.vtgarment.service.security.UserDetail;
import com.vtgarment.utils.Utils;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.List;

@Getter
@Setter
@ViewScoped
@ManagedBean(name = "reworkBean")
public class ReworkBean extends Bean {
    @ManagedProperty("#{reworkService}") private ReworkService reworkService;

    @NotNull private List<FactoryModel> factoryModelList;
    @NotNull private List<BuildingFloorModel> buildingFloorModelList;
    @NotNull private List<LineModel> lineModelList;
    @NotNull private List<ReworkTableView> reworkTableViewList;

    @NotNull private int factoryId;
    @NotNull private int buildingFloorId;
    @NotNull private int lineId;

    @NotNull private UserDetail userDetail;
    @NotNull private SummaryTableView summaryTableView;

    private int zero = 0;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation()");
        userDetail = getUser();
        init();
    }

    private void init(){
        factory();
        getRework();
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
        filterValue();
    }

    public void filterLine(){
        log.debug("filterLine : {}", buildingFloorId);
        lineId = 0;
        lineModelList = reworkService.findLineByBuildingFloorId(buildingFloorId);
        filterValue();
    }

    public void filterValue(){
        log.debug("Factory : {}, BuildingFloor : {}, Line : {}", factoryId, buildingFloorId, lineId);

        if (!Utils.isZero(factoryId)){
            reworkTableViewList = reworkService.getRework(factoryId, buildingFloorId, lineId, zero);
        } else {
            reworkTableViewList = reworkService.getRework(factoryId, buildingFloorId, lineId, userDetail.getLeaderId());
        }

        sum(reworkTableViewList);
    }

    public void getRework(){
        reworkTableViewList = reworkService.getRework(factoryId, buildingFloorId, lineId, userDetail.getLeaderId());
        sum(reworkTableViewList);
    }

    private void sum(List<ReworkTableView> list){
        summaryTableView = reworkService.sum(list);
    }
}
