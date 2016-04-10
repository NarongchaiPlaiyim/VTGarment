package com.vtgarment.beans;

import com.sun.istack.internal.NotNull;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.model.view.breakdown.BreakDownView;
import com.vtgarment.model.view.otp.OtpView;
import com.vtgarment.model.view.outstading.OutstadingView;
import com.vtgarment.model.view.rework.ReworkView;
import com.vtgarment.service.OverAllService;
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
@ManagedBean(name = "overAllBean")
public class OverAllBean extends Bean {

    @ManagedProperty("#{overAllService}") private OverAllService overAllService;

    @NotNull private List<FactoryModel> factoryModelList;
    @NotNull private List<BuildingFloorModel> buildingFloorModelList;
    @NotNull private List<LineModel> lineModelList;

    @NotNull private OtpView otpView;
    @NotNull private ReworkView reworkView;
    @NotNull private OutstadingView outstadingView;
    @NotNull private BreakDownView breakDownView;

    @NotNull private int factoryId;
    @NotNull private int buildingFloorId;
    @NotNull private int lineId;

    @NotNull private UserDetail userDetail;
    private String lastUpdate;

    @PostConstruct
    public void onCreation(){
        log.debug("onCreation()");
        userDetail = getUser();
        log.debug("-- User Detail : {}", userDetail);
        init();
    }

    private void init(){
        factory();
        getOTP();
        getRework();
        getBreakDown();
        getOutstading();
        findLastUpdate();
    }

    private void getOTP(){
        otpView = overAllService.findOTPView(factoryId, buildingFloorId, userDetail.getLineId());
    }

    private void getRework(){
        reworkView = overAllService.findReworkView(factoryId, buildingFloorId, userDetail.getLineId());
    }

    private void getOutstading(){
        outstadingView = overAllService.findOutstadingView(factoryId, buildingFloorId, userDetail.getLineId());
    }

    private void getBreakDown(){
        breakDownView = overAllService.findBreakDownView(factoryId, buildingFloorId, userDetail.getLineId());
    }

    private void factory(){
        log.debug("factory");
        factoryId = 0;
        factoryModelList = overAllService.findAll();
    }

    public void filterBuildingFloor(){
        log.debug("filterBuildingFloor : {}", factoryId);
        buildingFloorId = 0;
        lineId = 0;
        buildingFloorModelList = overAllService.findBuildingFloorByFactoryId(factoryId);
        filterValue();
    }

    public void filterLine(){
        log.debug("filterLine : {}", buildingFloorId);
        lineId = 0;
        lineModelList = overAllService.findLineByBuildingFloorId(buildingFloorId);
        filterValue();
    }

    public void findLastUpdate(){
        lastUpdate = overAllService.getLastUpdate(factoryId, buildingFloorId, userDetail.getLineId());
        log.debug("lastUpdate : {}", lastUpdate);
    }

    public void filterValue(){
        log.debug("Factory : {}, BuildingFloor : {}, Line : {}", factoryId, buildingFloorId, lineId);

        if (!Utils.isZero(factoryId) || Utils.isZero(userDetail.getLineId())){
            otpView = overAllService.findOTPView(factoryId, buildingFloorId, String.valueOf(lineId));
            reworkView = overAllService.findReworkView(factoryId, buildingFloorId, String.valueOf(lineId));
            outstadingView = overAllService.findOutstadingView(factoryId, buildingFloorId, String.valueOf(lineId));
            breakDownView = overAllService.findBreakDownView(factoryId, buildingFloorId, String.valueOf(lineId));
            lastUpdate = overAllService.getLastUpdate(factoryId, buildingFloorId, String.valueOf(lineId));
            log.debug("----- {}", lastUpdate);
        } else {
            getOTP();
            getRework();
            getOutstading();
            getBreakDown();
            findLastUpdate();
        }
    }
}
