package com.vtgarment.beans;

import com.sun.istack.internal.NotNull;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.model.view.SummaryTableView;
import com.vtgarment.model.view.otp.OtpTableView;
import com.vtgarment.service.OtpService;
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
@ManagedBean(name = "otpBean")
public class OtpBean extends Bean {
    @ManagedProperty("#{otpService}") private OtpService otpService;



    @NotNull private List<FactoryModel> factoryModelList;
    @NotNull private List<BuildingFloorModel> buildingFloorModelList;
    @NotNull private List<LineModel> lineModelList;
    @NotNull private List<OtpTableView> otpTableViewList;

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
        getOtp();
    }

    private void factory(){
        log.debug("factory");
        factoryId = 0;
        factoryModelList = otpService.findAll();
    }

    public void filterBuildingFloor(){
        log.debug("filterBuildingFloor : {}", factoryId);
        buildingFloorId = 0;
        buildingFloorModelList = otpService.findBuildingFloorByFactoryId(factoryId);
        filterValue();
    }

    public void filterLine(){
        log.debug("filterLine : {}", buildingFloorId);
        lineId = 0;
        lineModelList = otpService.findLineByBuildingFloorId(buildingFloorId);
        filterValue();
    }

    public void filterValue(){
        log.debug("Factory : {}, BuildingFloor : {}, Line : {}", factoryId, buildingFloorId, lineId);

        if (!Utils.isZero(factoryId)){
            otpTableViewList = otpService.getOtp(factoryId, buildingFloorId, lineId);
        } else {
            otpTableViewList = otpService.getOtp(factoryId, buildingFloorId, userDetail.getLineId());
        }

        sum(otpTableViewList);
    }

    public void getOtp(){
        otpTableViewList = otpService.getOtp(factoryId, buildingFloorId, userDetail.getLineId());
        sum(otpTableViewList);
    }

    private void sum(List<OtpTableView> list){
        summaryTableView = otpService.sum(list);
    }
}
