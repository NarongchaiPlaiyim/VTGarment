package com.vtgarment.beans;

import com.sun.istack.internal.NotNull;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.service.BreakDownService;
import com.vtgarment.utils.FacesUtil;
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
@ManagedBean(name = "breakDownBean")
public class BreakDownBean extends Bean {
    @ManagedProperty("#{breakDownService}") private BreakDownService breakDownService;

    @NotNull private List<FactoryModel> factoryModelList;
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
        factoryModelList = breakDownService.findAll();
    }

    public void filterBuildingFloor(){
        log.debug("filterBuildingFloor : {}", factoryId);
        buildingFloorId = 0;
        buildingFloorModelList = breakDownService.findBuildingFloorByFactoryId(factoryId);
    }

    public void filterLine(){
        log.debug("filterLine : {}", buildingFloorId);
        lineId = 0;
        lineModelList = breakDownService.findLineByBuildingFloorId(buildingFloorId);
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
}
