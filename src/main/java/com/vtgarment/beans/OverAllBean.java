package com.vtgarment.beans;

import com.sun.istack.internal.NotNull;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.service.OverAllService;
import com.vtgarment.utils.Utils;
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
@ManagedBean(name = "overAllBean")
public class OverAllBean extends Bean {

    @ManagedProperty("#{overAllService}") private OverAllService overAllService;

    @NotNull private List<FactoryModel> factoryModelList;
    @NotNull private List<BuildingFloorModel> buildingFloorModelList;
    @NotNull private List<LineModel> lineModelList;

    @NotNull private int factoryId;
    @NotNull private int buildingFloorId;
    @NotNull private int lineId;

    private boolean flagBack = Boolean.TRUE;

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
        factoryModelList = overAllService.findAll();
    }

    public void filterBuildingFloor(){
        log.debug("filterBuildingFloor : {}", factoryId);
        buildingFloorId = 0;
        flagBack = Boolean.FALSE;
        buildingFloorModelList = overAllService.findBuildingFloorByFactoryId(factoryId);
    }

    public void filterLine(){
        log.debug("filterLine : {}", buildingFloorId);
        lineId = 0;
        lineModelList = overAllService.findLineByBuildingFloorId(buildingFloorId);
    }

    public void filterValue(){
        log.debug("Factory : {}, BuildingFloor : {}, Line : {}", factoryId, buildingFloorId, lineId);
    }

    public void onClickBtnBack(){
        if (!Utils.isZero(lineId)){
            lineModelList = new ArrayList<>();
        } else if (!Utils.isZero(buildingFloorId)){
            buildingFloorModelList = new ArrayList<>();
        } else {
            flagBack = Boolean.TRUE;
            factoryId = 0;
        }

    }
}
