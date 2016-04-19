package com.vtgarment.service;

import com.vtgarment.model.dao.BuildingFloorDAO;
import com.vtgarment.model.dao.FactoryDAO;
import com.vtgarment.model.dao.LineDAO;
import com.vtgarment.model.dao.OtpDAO;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.model.view.SummaryTableView;
import com.vtgarment.model.view.otp.OtpTableView;
import com.vtgarment.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class OtpService extends Service {
    @Resource private FactoryDAO factoryDAO;
    @Resource private BuildingFloorDAO buildingFloorDAO;
    @Resource private LineDAO lineDAO;
    @Resource private OtpDAO otpDAO;

    @Value("#{config['style.red']}") private String red;
    @Value("#{config['style.green']}") private String green;
    @Value("#{config['arrow.up.text']}") private String up;
    @Value("#{config['arrow.down.text']}") private String down;

    private final int twoDecimal = 2;

    public List<FactoryModel> findAll(){
        try {
            log.debug("findAll()");
            return factoryDAO.findAll();
        } catch (Exception e) {
            log.debug("Exception error findAll Factory : {}", e);
            return new ArrayList<>();
        }
    }

    public List<BuildingFloorModel> findBuildingFloorByFactoryId(int factoryId){
        return buildingFloorDAO.findByFactoryId(factoryId);
    }

    public List<LineModel> findLineByBuildingFloorId(int buildingFloorId){
        return lineDAO.findByBuildingFloorId(buildingFloorId);
    }

    public List<OtpTableView> getOtp(int factoryId, int buildingFloorId, String lineId){
        return otpDAO.getOtp(factoryId, buildingFloorId, lineId);
    }

    public SummaryTableView sum(List<OtpTableView> otpTableViewList){
        SummaryTableView summaryTableView = new SummaryTableView();
        for (OtpTableView otpTableView : otpTableViewList){
            summaryTableView.setTotalYesterDay(summaryTableView.getTotalYesterDay().add(otpTableView.getYesterDay()));
            summaryTableView.setTotalToDay(summaryTableView.getTotalToDay().add(otpTableView.getToDay()));
            summaryTableView.setTotalTrend(summaryTableView.getTotalTrend().add(otpTableView.getTrend()));

            summaryTableView.setSummaryTarget(summaryTableView.getSummaryTarget().add(otpTableView.getTarget()));
        }

        log.debug("---- Size : {}", otpTableViewList.size());

        if (!Utils.isZero(otpTableViewList.size())){
            BigDecimal divideValue = new BigDecimal(otpTableViewList.size());
            summaryTableView.setTotalYesterDay(summaryTableView.getTotalYesterDay().divide(divideValue, BigDecimal.ROUND_HALF_UP));
            summaryTableView.setTotalToDay(summaryTableView.getTotalToDay().divide(divideValue, BigDecimal.ROUND_HALF_UP));


            summaryTableView.setSummaryTarget(summaryTableView.getSummaryTarget().divide(divideValue, BigDecimal.ROUND_HALF_UP));

            if (Utils.compareMoreBigDecimal(summaryTableView.getTotalToDay(), summaryTableView.getSummaryTarget())){
                summaryTableView.setStyleTotalToDay(green);
            } else {
                summaryTableView.setStyleTotalToDay(red);
            }

            if (Utils.compareMoreBigDecimal(summaryTableView.getTotalYesterDay(), summaryTableView.getSummaryTarget())){
                summaryTableView.setStyleTotalYesterDay(green);
            } else {
                summaryTableView.setStyleTotalYesterDay(red);
            }

            if (Utils.compareMoreBigDecimal(summaryTableView.getTotalToDay(), summaryTableView.getTotalYesterDay())){
                summaryTableView.setTotalTrend(summaryTableView.getTotalToDay().subtract(summaryTableView.getTotalYesterDay()).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                summaryTableView.setImageTrend(up);
            } else {
                summaryTableView.setTotalTrend(summaryTableView.getTotalYesterDay().subtract(summaryTableView.getTotalToDay()).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                summaryTableView.setImageTrend(down);
            }


        }

        log.debug("sum : {}", summaryTableView.toString());
        return summaryTableView;
    }

    public String getLastUpdate(int factory, int buildingFloor, String lineId){
        return lineDAO.findLastUpdate(factory, buildingFloor, lineId);
    }
}
