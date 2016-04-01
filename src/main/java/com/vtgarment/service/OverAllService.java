package com.vtgarment.service;

import com.vtgarment.model.dao.BuildingFloorDAO;
import com.vtgarment.model.dao.FactoryDAO;
import com.vtgarment.model.dao.LineDAO;
import com.vtgarment.model.dao.OverAllDAO;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.model.view.BreakDownView;
import com.vtgarment.model.view.OTPView;
import com.vtgarment.model.view.OutstadingView;
import com.vtgarment.model.view.ReworkView;
import com.vtgarment.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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
    @Resource private OverAllDAO overAllDAO;

    @Value("#{config['arrow.up']}") private String up;
    @Value("#{config['arrow.down']}") private String down;
    @Value("#{config['style.trendGreen']}") private String trendGreen;
    @Value("#{config['style.trendRed']}") private String trendRed;
    @Value("#{config['style.bestGreen']}") private String bestGreen;
    @Value("#{config['style.bestRed']}") private String bestRed;
    @Value("#{config['style.worstGreen']}") private String worstGreen;
    @Value("#{config['style.worstRed']}") private String worstRed;


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

    public OTPView findOTPView(int factory, int buildingFloor, int lineId){
        log.debug("-- findOTPView {} : {} : {}", factory, buildingFloor, lineId);
        OTPView otpView = null;

        if (Utils.isZero(factory) && !Utils.isZero(lineId)){
            otpView = overAllDAO.findOTPView(lineId);
        } else {
            otpView = overAllDAO.findOTPViewBySection(factory, buildingFloor, lineId);
        }

        if (!Utils.isNull(otpView.getTrendActual()) && !Utils.isNull(otpView.getTrendTarget())){
            if (Utils.compareBigDecimal(otpView.getTrendActual(), otpView.getTrendTarget())){
                otpView.setImage(up);
                otpView.setStyleTrend(trendGreen);
            } else {
                otpView.setImage(down);
                otpView.setStyleTrend(trendRed);
            }
        }

        if (!Utils.isNull(otpView.getBestActual()) && !Utils.isNull(otpView.getBestTarget())){
            if (Utils.compareBigDecimal(otpView.getBestActual(), otpView.getBestTarget())){
                otpView.setStyleBest(bestGreen);
            } else {
                otpView.setStyleBest(bestRed);
            }
        }

        if (!Utils.isNull(otpView.getWorstActual()) && !Utils.isNull(otpView.getWorstTarget())){
            if (Utils.compareBigDecimal(otpView.getWorstActual(), otpView.getWorstTarget())){
                otpView.setStyleWorst(worstGreen);
            } else {
                otpView.setStyleWorst(worstRed);
            }
        }

        return otpView;
    }

    public ReworkView findReworkView(int factory, int buildingFloor, int lineId){
        log.debug("-- findReworkView {} : {} : {}", factory, buildingFloor, lineId);
        ReworkView reworkView = null;

        if (Utils.isZero(factory) && !Utils.isZero(lineId)){
            reworkView = overAllDAO.findReworkView(lineId);
        } else {
            reworkView = overAllDAO.findReworkViewBySection(factory, buildingFloor, lineId);
        }

        if (!Utils.isNull(reworkView.getTrendActual()) && !Utils.isNull(reworkView.getTrendTarget())){
            if (Utils.compareBigDecimal(reworkView.getTrendActual(), reworkView.getTrendTarget())){
                reworkView.setImage(down);
                reworkView.setStyleTrend(trendRed);
            } else {
                reworkView.setImage(up);
                reworkView.setStyleTrend(trendGreen);
            }
        }

        if (!Utils.isNull(reworkView.getBestActual()) && !Utils.isNull(reworkView.getBestTarget())){
            if (Utils.compareBigDecimal(reworkView.getBestActual(), reworkView.getBestTarget())){
                reworkView.setStyleBest(bestRed);
            } else {
                reworkView.setStyleBest(bestGreen);
            }
        }

        if (!Utils.isNull(reworkView.getWorstActual()) && !Utils.isNull(reworkView.getWorstTarget())){
            if (Utils.compareBigDecimal(reworkView.getWorstActual(), reworkView.getWorstTarget())){
                reworkView.setStyleWorst(worstRed);
            } else {
                reworkView.setStyleWorst(worstGreen);
            }
        }

        return reworkView;
    }

    public BreakDownView findBreakDownView(int factory, int buildingFloor, int lineId){
        log.debug("-- findBreakDownView {} : {} : {}", factory, buildingFloor, lineId);
        BreakDownView breakDownView = null;

        if (Utils.isZero(factory) && !Utils.isZero(lineId)){
            breakDownView = overAllDAO.findBreakDownView(lineId);
        } else {
            breakDownView = overAllDAO.findBreakDownViewBySection(factory, buildingFloor, lineId);
        }

        if (!Utils.isNull(breakDownView.getTrendActual()) && !Utils.isNull(breakDownView.getTrendTarget())){
            if (Utils.compareBigDecimal(breakDownView.getTrendActual(), breakDownView.getTrendTarget())){
                breakDownView.setImage(down);
                breakDownView.setStyleTrend(trendRed);
            } else {
                breakDownView.setImage(up);
                breakDownView.setStyleTrend(trendGreen);
            }
        }

        if (!Utils.isNull(breakDownView.getBestActual()) && !Utils.isNull(breakDownView.getBestTarget())){
            if (Utils.compareBigDecimal(breakDownView.getBestActual(), breakDownView.getBestTarget())){
                breakDownView.setStyleBest(bestRed);
            } else {
                breakDownView.setStyleBest(bestGreen);
            }
        }

        if (!Utils.isNull(breakDownView.getWorstActual()) && !Utils.isNull(breakDownView.getWorstTarget())){
            if (Utils.compareBigDecimal(breakDownView.getWorstActual(), breakDownView.getWorstTarget())){
                breakDownView.setStyleWorst(worstRed);
            } else {
                breakDownView.setStyleWorst(worstGreen);
            }
        }

        return breakDownView;
    }

    public OutstadingView findOutstadingView(int factory, int buildingFloor, int lineId){
        log.debug("-- findOutstadingView {} : {} : {}", factory, buildingFloor, lineId);
        OutstadingView outstadingView = null;

        if (Utils.isZero(factory) && !Utils.isZero(lineId)){
            outstadingView = overAllDAO.findOutstadingView(lineId);
        } else {
            outstadingView = overAllDAO.findOutstadingViewBySection(factory, buildingFloor, lineId);
        }

        if (!Utils.isNull(outstadingView.getTrendActual()) && !Utils.isNull(outstadingView.getTrendTarget())){
            if (Utils.compareBigDecimal(outstadingView.getTrendActual(), outstadingView.getTrendTarget())){
                outstadingView.setImage(down);
                outstadingView.setStyleTrend(trendRed);
            } else {
                outstadingView.setImage(up);
                outstadingView.setStyleTrend(trendGreen);
            }
        }

        if (!Utils.isNull(outstadingView.getBestActual()) && !Utils.isNull(outstadingView.getBestTarget())){
            if (Utils.compareBigDecimal(outstadingView.getBestActual(), outstadingView.getBestTarget())){
                outstadingView.setStyleBest(bestRed);
            } else {
                outstadingView.setStyleBest(bestGreen);
            }
        }

        if (!Utils.isNull(outstadingView.getWorstActual()) && !Utils.isNull(outstadingView.getWorstTarget())){
            if (Utils.compareBigDecimal(outstadingView.getWorstActual(), outstadingView.getWorstTarget())){
                outstadingView.setStyleWorst(worstRed);
            } else {
                outstadingView.setStyleWorst(worstGreen);
            }
        }

        return outstadingView;
    }
}
