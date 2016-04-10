package com.vtgarment.service;

import com.vtgarment.model.dao.*;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.model.view.breakdown.BreakDownCompareView;
import com.vtgarment.model.view.breakdown.BreakDownView;
import com.vtgarment.model.view.otp.OtpView;
import com.vtgarment.model.view.outstading.OutstadingView;
import com.vtgarment.model.view.rework.ReworkView;
import com.vtgarment.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pakorn on 29/03/2016.
 */
@Component
@Transactional
public class OverAllService extends Service{

    @Resource private FactoryDAO factoryDAO;
    @Resource private BuildingFloorDAO buildingFloorDAO;
    @Resource private LineDAO lineDAO;
    @Resource private DashboardDAO overAllDAO;
    @Resource private ProductionDAO productionDAO;

    @Value("#{config['arrow.up']}") private String up;
    @Value("#{config['arrow.down']}") private String down;
    @Value("#{config['style.trendGreen']}") private String trendGreen;
    @Value("#{config['style.trendRed']}") private String trendRed;
    @Value("#{config['style.bestGreen']}") private String bestGreen;
    @Value("#{config['style.bestRed']}") private String bestRed;
    @Value("#{config['style.worstGreen']}") private String worstGreen;
    @Value("#{config['style.worstRed']}") private String worstRed;

    public String getLastUpdate(int factory, int buildingFloor, String lineId){
        return lineDAO.findLastUpdate(factory, buildingFloor, lineId);
    }

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

    public OtpView findOTPView(int factory, int buildingFloor, String lineId){
        log.debug("-- findOTPView {} : {} : {}", factory, buildingFloor, lineId);
        OtpView otpView = null;

//        if (Utils.isZero(factory) && !Utils.isZero(lineId)){
//            otpView = overAllDAO.findOTPView(lineId);
//        } else {
            otpView = overAllDAO.findOTPViewBySection(factory, buildingFloor, lineId);
//        }

        if (!Utils.isNull(otpView.getTrendActual()) && !Utils.isNull(otpView.getTrendTarget())){
            if (Utils.compareMoreBigDecimal(otpView.getTrendActual(), otpView.getTrendTarget())){
                otpView.setImage(up);
                otpView.setStyleTrend(trendGreen);
            } else {
                otpView.setImage(down);
                otpView.setStyleTrend(trendRed);
            }
        }

        if (!Utils.isNull(otpView.getBestActual()) && !Utils.isNull(otpView.getBestTarget())){
            if (Utils.compareMoreBigDecimal(otpView.getBestActual(), otpView.getBestTarget())){
                otpView.setStyleBest(bestGreen);
            } else {
                otpView.setStyleBest(bestRed);
            }
        }

        if (!Utils.isNull(otpView.getWorstActual()) && !Utils.isNull(otpView.getWorstTarget())){
            if (Utils.compareMoreBigDecimal(otpView.getWorstActual(), otpView.getWorstTarget())){
                otpView.setStyleWorst(worstGreen);
            } else {
                otpView.setStyleWorst(worstRed);
            }
        }

        return otpView;
    }

    public ReworkView findReworkView(int factory, int buildingFloor, String lineId){
        log.debug("-- findReworkView {} : {} : {}", factory, buildingFloor, lineId);
        ReworkView reworkView = null;

//        if (Utils.isZero(factory) && !Utils.isZero(lineId)){
//            reworkView = overAllDAO.findReworkView(lineId);
//        } else {
            reworkView = overAllDAO.findReworkViewBySection(factory, buildingFloor, lineId);
//        }

        if (!Utils.isNull(reworkView.getTrendActual()) && !Utils.isNull(reworkView.getTrendTarget())){
            if (Utils.compareLessBigDecimal(reworkView.getTrendActual(), reworkView.getTrendTarget())){
                reworkView.setImage(up);
                reworkView.setStyleTrend(trendGreen);
            } else {
                reworkView.setImage(down);
                reworkView.setStyleTrend(trendRed);
            }
        }

        if (!Utils.isNull(reworkView.getBestActual()) && !Utils.isNull(reworkView.getBestTarget())){
            if (Utils.compareLessBigDecimal(reworkView.getBestActual(), reworkView.getBestTarget())){
                reworkView.setStyleBest(bestGreen);
            } else {
                reworkView.setStyleBest(bestRed);
            }
        }

        if (!Utils.isNull(reworkView.getWorstActual()) && !Utils.isNull(reworkView.getWorstTarget())){
            if (Utils.compareLessBigDecimal(reworkView.getWorstActual(), reworkView.getWorstTarget())){
                reworkView.setStyleWorst(worstGreen);
            } else {
                reworkView.setStyleWorst(worstRed);
            }
        }

        return reworkView;
    }

    public BreakDownView findBreakDownView(int factory, int buildingFloor, String lineId){
        log.debug("-- findBreakDownView {} : {} : {}", factory, buildingFloor, lineId);
        BreakDownView breakDownView = null;

        breakDownView = overAllDAO.findBreakDownViewBySection(factory, buildingFloor, lineId);
        BreakDownCompareView breakDownCompareView = productionDAO.getCompateBreakdown();

        int sum = breakDownCompareView.getMan() + breakDownCompareView.getMach() + breakDownCompareView.getMethod() + breakDownCompareView.getMaterial();

        BigDecimal compare = new BigDecimal(sum);
        if (!Utils.isNull(breakDownView.getTrendActual())){
            if (Utils.compareLessBigDecimal(breakDownView.getTrendActual(), compare)){
                breakDownView.setImage(up);
                breakDownView.setStyleTrend(trendGreen);
            } else {
                breakDownView.setImage(down);
                breakDownView.setStyleTrend(trendRed);
            }
        }

        if (!Utils.isNull(breakDownView.getBestActual())){
            if (Utils.compareLessBigDecimal(breakDownView.getBestActual(), compare)) {
                breakDownView.setStyleBest(bestGreen);
            } else {
                breakDownView.setStyleBest(bestRed);
            }
        }

        if (!Utils.isNull(breakDownView.getWorstActual())){
            if (Utils.compareLessBigDecimal(breakDownView.getWorstActual(), compare)) {
                breakDownView.setStyleWorst(worstGreen);
            } else {
                breakDownView.setStyleWorst(worstRed);
            }
        }

        return breakDownView;
    }

    public OutstadingView findOutstadingView(int factory, int buildingFloor, String lineId){
        log.debug("-- findOutstadingView {} : {} : {}", factory, buildingFloor, lineId);
        OutstadingView outstadingView = null;

//        if (Utils.isZero(factory) && !Utils.isZero(lineId)){
//            outstadingView = overAllDAO.findOutstadingView(lineId);
//        } else {
            outstadingView = overAllDAO.findOutstadingViewBySection(factory, buildingFloor, lineId);
//        }

        if (!Utils.isNull(outstadingView.getTrendActual()) && !Utils.isNull(outstadingView.getTrendTarget())){
            if (Utils.compareInt(outstadingView.getTrendActual(), outstadingView.getTrendTarget())){
                outstadingView.setImage(up);
                outstadingView.setStyleTrend(trendGreen);
            } else {
                outstadingView.setImage(down);
                outstadingView.setStyleTrend(trendRed);
            }
        }

        if (!Utils.isNull(outstadingView.getBestActual()) && !Utils.isNull(outstadingView.getBestTarget())){
            if (Utils.compareInt(outstadingView.getBestActual(), outstadingView.getBestTarget())){
                outstadingView.setStyleBest(bestGreen);
            } else {
                outstadingView.setStyleBest(bestRed);
            }
        }

        if (!Utils.isNull(outstadingView.getWorstActual()) && !Utils.isNull(outstadingView.getWorstTarget())){
            if (Utils.compareInt(outstadingView.getWorstActual(), outstadingView.getWorstTarget())){
                outstadingView.setStyleWorst(worstGreen);
            } else {
                outstadingView.setStyleWorst(worstRed);
            }
        }

        return outstadingView;
    }
}
