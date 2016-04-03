package com.vtgarment.beans;

import com.sun.istack.internal.NotNull;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.model.view.SummaryTableView;
import com.vtgarment.model.view.outstading.OutStadingTableView;
import com.vtgarment.service.OutStadingService;
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
@ManagedBean(name = "outStadingBean")
public class OutstadingBean extends Bean {
    @ManagedProperty("#{outStadingService}") private OutStadingService outStadingService;

    @NotNull private List<FactoryModel> factoryModelList;
    @NotNull private List<BuildingFloorModel> buildingFloorModelList;
    @NotNull private List<LineModel> lineModelList;
    @NotNull private List<OutStadingTableView> outStadingTableViewList;

    @NotNull private int factoryId;
    @NotNull private int buildingFloorId;
    @NotNull private int lineId;

    @NotNull private UserDetail userDetail;
    @NotNull private SummaryTableView summaryTableView;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation()");
        userDetail = getUser();
        init();
    }

    private void init(){
        factory();
        getOutStading();
    }

    private void factory(){
        log.debug("factory");
        factoryId = 0;
        factoryModelList = outStadingService.findAll();
    }

    public void filterBuildingFloor(){
        log.debug("filterBuildingFloor : {}", factoryId);
        buildingFloorId = 0;
        buildingFloorModelList = outStadingService.findBuildingFloorByFactoryId(factoryId);
        filterValue();
    }

    public void filterLine(){
        log.debug("filterLine : {}", buildingFloorId);
        lineId = 0;
        lineModelList = outStadingService.findLineByBuildingFloorId(buildingFloorId);
        filterValue();
    }

    public void filterValue(){
        log.debug("Factory : {}, BuildingFloor : {}, Line : {}", factoryId, buildingFloorId, lineId);

        if (!Utils.isZero(factoryId)){
            outStadingTableViewList = outStadingService.getOutstading(factoryId, buildingFloorId, lineId);
        } else {
            outStadingTableViewList = outStadingService.getOutstading(factoryId, buildingFloorId, userDetail.getLineId());
        }

        sum(outStadingTableViewList);
    }

    public void getOutStading(){
        log.debug("Factory : {}, BuildingFloor : {}, Line : {}", factoryId, buildingFloorId, userDetail.getLineId());
        outStadingTableViewList = outStadingService.getOutstading(factoryId, buildingFloorId, userDetail.getLineId());
        sum(outStadingTableViewList);
    }

    private void sum(List<OutStadingTableView> list){
        summaryTableView = outStadingService.sum(list);
    }

    public void onClickBtnBack(){
        FacesUtil.redirect("/site/overAll.xhtml");
    }
}
