package com.vtgarment.service;

import com.vtgarment.model.dao.*;
import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.model.db.FactoryModel;
import com.vtgarment.model.db.LineModel;
import com.vtgarment.model.view.SummaryBreakDownTableView;
import com.vtgarment.model.view.breakdown.BreakDownCompareView;
import com.vtgarment.model.view.breakdown.BreakDownTableView;
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
public class BreakDownService extends Service {
    @Resource private FactoryDAO factoryDAO;
    @Resource private BuildingFloorDAO buildingFloorDAO;
    @Resource private LineDAO lineDAO;
    @Resource private BreakDownDAO breakDownDAO;
    @Resource private ProductionDAO productionDAO;
    private final int twoDecimal = 2;

    @Value("#{config['style.red']}") private String red;
    @Value("#{config['style.green']}") private String green;
    @Value("#{config['arrow.up.text']}") private String up;
    @Value("#{config['arrow.down.text']}") private String down;

    private BreakDownCompareView breakDownCompareView;

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

    public List<BreakDownTableView> getBreakDown(int factoryId, int buildingFloorId, String lineId){
        breakDownCompareView = productionDAO.getCompateBreakdown();
        return breakDownDAO.getBreakDown(factoryId, buildingFloorId, lineId, breakDownCompareView);
    }

    public SummaryBreakDownTableView sum(List<BreakDownTableView> breakDownTableViews){
        SummaryBreakDownTableView summaryBreakDownTableView = new SummaryBreakDownTableView();

        BigDecimal man = new BigDecimal(breakDownCompareView.getMan());
        BigDecimal mach = new BigDecimal(breakDownCompareView.getMach());
        BigDecimal method = new BigDecimal(breakDownCompareView.getMethod());
        BigDecimal material = new BigDecimal(breakDownCompareView.getMaterial());
        BigDecimal sum = new BigDecimal(breakDownCompareView.getMan() + breakDownCompareView.getMach() + breakDownCompareView.getMethod() + breakDownCompareView.getMaterial());

        for (BreakDownTableView breakDownTableView : breakDownTableViews){
            summaryBreakDownTableView.setTotalPeopleToDay(summaryBreakDownTableView.getTotalPeopleToDay().add(breakDownTableView.getToDayPeople()));
            summaryBreakDownTableView.setTotalMachToDay(summaryBreakDownTableView.getTotalMachToDay().add(breakDownTableView.getToDayMach()));
            summaryBreakDownTableView.setTotalMethodToDay(summaryBreakDownTableView.getTotalMethodToDay().add(breakDownTableView.getToDayMethod()));
            summaryBreakDownTableView.setTotalMaterialToDay(summaryBreakDownTableView.getTotalMaterialToDay().add(breakDownTableView.getToDayMaterial()));
            summaryBreakDownTableView.setTotalAllToDay(summaryBreakDownTableView.getTotalAllToDay().add(breakDownTableView.getToDayTotal()));


            summaryBreakDownTableView.setTotalPeopleYesterDay(summaryBreakDownTableView.getTotalPeopleYesterDay().add(breakDownTableView.getYesterDayPeople()));
            summaryBreakDownTableView.setTotalMachYesterDay(summaryBreakDownTableView.getTotalMachYesterDay().add(breakDownTableView.getYesterDayMach()));
            summaryBreakDownTableView.setTotalMethodYesterDay(summaryBreakDownTableView.getTotalMethodYesterDay().add(breakDownTableView.getYesterDayMethod()));
            summaryBreakDownTableView.setTotalMaterialYesterDay(summaryBreakDownTableView.getTotalMaterialYesterDay().add(breakDownTableView.getYesterDayMaterial()));
            summaryBreakDownTableView.setTotalAllYesterDay(summaryBreakDownTableView.getTotalAllYesterDay().add(breakDownTableView.getYesterDayTotal()));

            summaryBreakDownTableView.setTotalTrend(summaryBreakDownTableView.getTotalTrend().add(breakDownTableView.getTrend()));
        }

        log.debug("---- Size : {}", breakDownTableViews.size());

        if (!Utils.isZero(breakDownTableViews.size())){
            BigDecimal divideValue = new BigDecimal(breakDownTableViews.size());

            summaryBreakDownTableView.setTotalPeopleToDay(summaryBreakDownTableView.getTotalPeopleToDay().divide(divideValue, BigDecimal.ROUND_HALF_UP).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            summaryBreakDownTableView.setTotalMachToDay(summaryBreakDownTableView.getTotalMachToDay().divide(divideValue, BigDecimal.ROUND_HALF_UP).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            summaryBreakDownTableView.setTotalMethodToDay(summaryBreakDownTableView.getTotalMethodToDay().divide(divideValue, BigDecimal.ROUND_HALF_UP).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            summaryBreakDownTableView.setTotalMaterialToDay(summaryBreakDownTableView.getTotalMaterialToDay().divide(divideValue, BigDecimal.ROUND_HALF_UP).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            summaryBreakDownTableView.setTotalAllToDay(summaryBreakDownTableView.getTotalAllToDay().divide(divideValue, BigDecimal.ROUND_HALF_UP).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));


            summaryBreakDownTableView.setTotalPeopleYesterDay(summaryBreakDownTableView.getTotalPeopleYesterDay().divide(divideValue, BigDecimal.ROUND_HALF_UP).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            summaryBreakDownTableView.setTotalMachYesterDay(summaryBreakDownTableView.getTotalMachYesterDay().divide(divideValue, BigDecimal.ROUND_HALF_UP).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            summaryBreakDownTableView.setTotalMethodYesterDay(summaryBreakDownTableView.getTotalMethodYesterDay().divide(divideValue, BigDecimal.ROUND_HALF_UP).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            summaryBreakDownTableView.setTotalMaterialYesterDay(summaryBreakDownTableView.getTotalMaterialYesterDay().divide(divideValue, BigDecimal.ROUND_HALF_UP).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            summaryBreakDownTableView.setTotalAllYesterDay(summaryBreakDownTableView.getTotalAllYesterDay().divide(divideValue, BigDecimal.ROUND_HALF_UP).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

            summaryBreakDownTableView.setTotalTrend(summaryBreakDownTableView.getTotalTrend().divide(divideValue, BigDecimal.ROUND_HALF_UP).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));


            // To Day
            if (Utils.compareLessBigDecimal(summaryBreakDownTableView.getTotalPeopleToDay(), man)){
                summaryBreakDownTableView.setStylePeopleToDay(green);
            } else {
                summaryBreakDownTableView.setStylePeopleToDay(red);
            }

            if (Utils.compareLessBigDecimal(summaryBreakDownTableView.getTotalMachToDay(), mach)){
                summaryBreakDownTableView.setStyleMachToDay(green);
            } else {
                summaryBreakDownTableView.setStyleMachToDay(red);
            }

            if (Utils.compareLessBigDecimal(summaryBreakDownTableView.getTotalMethodToDay(), method)){
                summaryBreakDownTableView.setStyleMethodToDay(green);
            } else {
                summaryBreakDownTableView.setStyleMethodToDay(red);
            }

            if (Utils.compareLessBigDecimal(summaryBreakDownTableView.getTotalMaterialToDay(), material)){
                summaryBreakDownTableView.setStyleMaterialToDay(green);
            } else {
                summaryBreakDownTableView.setStyleMaterialToDay(red);
            }

            if (Utils.compareLessBigDecimal(summaryBreakDownTableView.getTotalAllToDay(), sum)){
                summaryBreakDownTableView.setStyleAllToDay(green);
            } else {
                summaryBreakDownTableView.setStyleAllToDay(red);
            }

            // Total
            if (Utils.compareLessBigDecimal(summaryBreakDownTableView.getTotalTrend(), sum)){
                summaryBreakDownTableView.setImageSummaryTrend(up);
            } else {
                summaryBreakDownTableView.setImageSummaryTrend(down);
            }

            //Yester Day
            if (Utils.compareLessBigDecimal(summaryBreakDownTableView.getTotalPeopleYesterDay(), man)){
                summaryBreakDownTableView.setStylePeopleYesterDay(green);
            } else {
                summaryBreakDownTableView.setStylePeopleYesterDay(red);
            }

            if (Utils.compareLessBigDecimal(summaryBreakDownTableView.getTotalMachYesterDay(), mach)){
                summaryBreakDownTableView.setStyleMachYesterDay(green);
            } else {
                summaryBreakDownTableView.setStyleMachYesterDay(red);
            }

            if (Utils.compareLessBigDecimal(summaryBreakDownTableView.getTotalMethodYesterDay(), method)){
                summaryBreakDownTableView.setStyleMethodYesterDay(green);
            } else {
                summaryBreakDownTableView.setStyleMethodYesterDay(red);
            }

            if (Utils.compareLessBigDecimal(summaryBreakDownTableView.getTotalMaterialYesterDay(), material)){
                summaryBreakDownTableView.setStyleMaterialYesterDay(green);
            } else {
                summaryBreakDownTableView.setStyleMaterialYesterDay(red);
            }

            if (Utils.compareLessBigDecimal(summaryBreakDownTableView.getTotalAllYesterDay(), sum)){
                summaryBreakDownTableView.setStyleAllYesterDay(green);
            } else {
                summaryBreakDownTableView.setStyleAllYesterDay(red);
            }


        }

        return summaryBreakDownTableView;
    }

    public String getLastUpdate(int factory, int buildingFloor, String lineId){
        return lineDAO.findLastUpdateBreakdown(factory, buildingFloor, lineId);
    }
}
