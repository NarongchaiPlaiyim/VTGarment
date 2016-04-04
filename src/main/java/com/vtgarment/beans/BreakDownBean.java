package com.vtgarment.beans;

import com.sun.istack.internal.NotNull;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.model.view.SummaryBreakDownTableView;
import com.vtgarment.model.view.breakdown.BreakDownTableView;
import com.vtgarment.service.BreakDownService;
import com.vtgarment.service.security.UserDetail;
import com.vtgarment.utils.FacesUtil;
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
@ManagedBean(name = "breakDownBean")
public class BreakDownBean extends Bean {
    @ManagedProperty("#{breakDownService}") private BreakDownService breakDownService;

    @NotNull private List<FactoryModel> factoryModelList;
    @NotNull private List<BuildingFloorModel> buildingFloorModelList;
    @NotNull private List<LineModel> lineModelList;
    @NotNull private List<BreakDownTableView> breakDownTableViewList;

    @NotNull private int factoryId;
    @NotNull private int buildingFloorId;
    @NotNull private int lineId;

    @NotNull private UserDetail userDetail;
    @NotNull private SummaryBreakDownTableView summaryBreakDownTableView;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation()");
        userDetail = getUser();
        init();
    }

    private void init(){
        factory();
        getBreakDown();
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
        filterValue();
    }

    public void filterLine(){
        log.debug("filterLine : {}", buildingFloorId);
        lineId = 0;
        lineModelList = breakDownService.findLineByBuildingFloorId(buildingFloorId);
        filterValue();
    }

    public void filterValue(){
        log.debug("Factory : {}, BuildingFloor : {}, Line : {}", factoryId, buildingFloorId, lineId);

        if (!Utils.isZero(factoryId)){
            breakDownTableViewList = breakDownService.getBreakDown(factoryId, buildingFloorId, lineId);
        } else {
            breakDownTableViewList = breakDownService.getBreakDown(factoryId, buildingFloorId, userDetail.getLineId());
        }
    }

    private void getBreakDown(){
        log.debug("Factory : {}, BuildingFloor : {}, Line : {}", factoryId, buildingFloorId, userDetail.getLineId());
        breakDownTableViewList = breakDownService.getBreakDown(factoryId, buildingFloorId, userDetail.getLineId());
        sum(breakDownTableViewList);
    }

    private void sum(List<BreakDownTableView> list){
        summaryBreakDownTableView = breakDownService.sum(list);
    }

    public void onClickBtnBack(){
        FacesUtil.redirect("/site/overAll.xhtml");
    }
}
