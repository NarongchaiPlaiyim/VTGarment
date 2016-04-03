package com.vtgarment.service;

import com.vtgarment.model.dao.BuildingFloorDAO;
import com.vtgarment.model.dao.FactoryDAO;
import com.vtgarment.model.dao.LineDAO;
import com.vtgarment.model.dao.ReworkDAO;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.model.view.SummaryTableView;
import com.vtgarment.model.view.rework.ReworkTableView;
import com.vtgarment.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pakorn on 03/04/2016.
 */
@Component
@Transactional
public class ReworkService extends Service{
    @Resource private FactoryDAO factoryDAO;
    @Resource private BuildingFloorDAO buildingFloorDAO;
    @Resource private LineDAO lineDAO;
    @Resource private ReworkDAO reworkDAO;

    @Value("#{config['style.red']}") private String red;
    @Value("#{config['style.green']}") private String green;
    @Value("#{config['arrow.up.text']}") private String up;
    @Value("#{config['arrow.down.text']}") private String down;

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

    public List<ReworkTableView> getRework(int factoryId, int buildingFloorId, int lineId){
        return reworkDAO.getRework(factoryId, buildingFloorId, lineId);
    }

    public SummaryTableView sum(List<ReworkTableView> reworkTableViewList){
        SummaryTableView summaryTableView = new SummaryTableView();
        for (ReworkTableView reworkTableView : reworkTableViewList){
            summaryTableView.setTotalYesterDay(summaryTableView.getTotalYesterDay().add(reworkTableView.getYesterDay()));
            summaryTableView.setTotalToDay(summaryTableView.getTotalToDay().add(reworkTableView.getToDay()));
            summaryTableView.setTotalTrend(summaryTableView.getTotalTrend().add(reworkTableView.getTrend()));
        }

        log.debug("---- Size : {}", reworkTableViewList.size());

        if (!Utils.isZero(reworkTableViewList.size())){
            BigDecimal divideValue = new BigDecimal(reworkTableViewList.size());
            summaryTableView.setTotalYesterDay(summaryTableView.getTotalYesterDay().divide(divideValue, BigDecimal.ROUND_HALF_UP));
            summaryTableView.setTotalToDay(summaryTableView.getTotalToDay().divide(divideValue, BigDecimal.ROUND_HALF_UP));
            summaryTableView.setTotalTrend(summaryTableView.getTotalTrend().divide(divideValue, BigDecimal.ROUND_HALF_UP));

            if (Utils.compareBigDecimal(summaryTableView.getTotalToDay(), summaryTableView.getTotalYesterDay())){
                summaryTableView.setStyleTotalToDay(green);
                summaryTableView.setStyleTotalYesterDay(red);
                summaryTableView.setImageTrend(up);
            } else {
                summaryTableView.setStyleTotalToDay(red);
                summaryTableView.setStyleTotalYesterDay(green);
                summaryTableView.setImageTrend(down);
            }
        }

        return summaryTableView;
    }
}
