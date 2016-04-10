package com.vtgarment.service;

import com.vtgarment.model.dao.BuildingFloorDAO;
import com.vtgarment.model.dao.FactoryDAO;
import com.vtgarment.model.dao.LineDAO;
import com.vtgarment.model.dao.OutStadingDAO;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.model.view.SummaryTableView;
import com.vtgarment.model.view.outstading.OutStadingTableView;
import com.vtgarment.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
@Transactional
public class OutStandingService extends Service  {
    @Resource private FactoryDAO factoryDAO;
    @Resource private BuildingFloorDAO buildingFloorDAO;
    @Resource private LineDAO lineDAO;
    @Resource private OutStadingDAO outStadingDAO;

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

    public List<OutStadingTableView> getOutstading(int factoryId, int buildingFloorId, String lineId){
        return outStadingDAO.getOutStading(factoryId, buildingFloorId, lineId);
    }

    public SummaryTableView sum(List<OutStadingTableView> outStadingTableViewList){
        SummaryTableView summaryTableView = new SummaryTableView();
        for (OutStadingTableView outStadingTableView : outStadingTableViewList){
            summaryTableView.setTotalYesterDayOutStading(summaryTableView.getTotalYesterDayOutStading() + outStadingTableView.getYesterDay());
            summaryTableView.setTotalToDayOutStading(summaryTableView.getTotalToDayOutStading() + outStadingTableView.getToDay());
            summaryTableView.setTotalTrendOutStading(summaryTableView.getTotalTrendOutStading() + outStadingTableView.getTrend());
        }

        log.debug("---------- {}", summaryTableView.getTotalTrendOutStading());

        if (!Utils.isZero(outStadingTableViewList.size())){
            int divideValue = outStadingTableViewList.size();
            summaryTableView.setTotalYesterDayOutStading(summaryTableView.getTotalYesterDayOutStading()/divideValue);
            summaryTableView.setTotalToDayOutStading(summaryTableView.getTotalToDayOutStading()/divideValue);
            summaryTableView.setTotalTrendOutStading(summaryTableView.getTotalTrendOutStading()/divideValue);

            if (Utils.compareInt(summaryTableView.getTotalToDayOutStading(), summaryTableView.getTotalYesterDayOutStading())){
                summaryTableView.setStyleTotalToDay(red);
                summaryTableView.setStyleTotalYesterDay(green);
                summaryTableView.setImageTrend(down);
            } else {
                summaryTableView.setStyleTotalToDay(green);
                summaryTableView.setStyleTotalYesterDay(red);
                summaryTableView.setImageTrend(up);
            }
        }

        return summaryTableView;
    }

    public String getLastUpdate(int factory, int buildingFloor, String lineId){
        return lineDAO.findLastUpdate(factory, buildingFloor, lineId);
    }
}
