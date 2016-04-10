package com.vtgarment.model.dao;

import com.vtgarment.model.view.breakdown.BreakDownCompareView;
import com.vtgarment.model.view.breakdown.BreakDownTableView;
import com.vtgarment.utils.Utils;
import org.hibernate.SQLQuery;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pakorn on 03/04/2016.
 */
@Repository
public class BreakDownDAO extends GenericDAO<String, Integer> {
    private final int twoDecimal = 2;
    @Value("#{config['style.red']}") private String red;
    @Value("#{config['style.green']}") private String green;
    @Value("#{config['arrow.up']}") private String up;
    @Value("#{config['arrow.down']}") private String down;

    public List<BreakDownTableView> getBreakDown(int factoryId, int bildingFloorId, String lineId, BreakDownCompareView breakDownCompareView){
        List<BreakDownTableView> breakDownTableViewList = new ArrayList<>();

        BigDecimal man = new BigDecimal(breakDownCompareView.getMan());
        BigDecimal mach = new BigDecimal(breakDownCompareView.getMach());
        BigDecimal method = new BigDecimal(breakDownCompareView.getMethod());
        BigDecimal material = new BigDecimal(breakDownCompareView.getMaterial());
        BigDecimal sum = new BigDecimal(breakDownCompareView.getMan() + breakDownCompareView.getMach() + breakDownCompareView.getMethod() + breakDownCompareView.getMaterial());

        StringBuilder whereSQL = new StringBuilder();

        StringBuilder sql = new StringBuilder();

        if (!Utils.isZero(factoryId)){
            whereSQL.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(bildingFloorId)){
            whereSQL.append(" AND building_floor.id =").append(bildingFloorId);
        }

        if (!Utils.isNull(lineId) && !Utils.isEmpty(lineId) && !"0".equals(lineId)){
            whereSQL.append(" AND production.line_id in (").append(lineId).append(")");
        }

        sql.append(" SELECT code||line.name AS LINE_CODE,")
                .append(" people_today.today_people AS TODAY_PEOPLE, mach_today.today_mach AS TODAY_MACH, method_today.today_method AS TODAY_METHOD, material_today.today_material AS TODAY_MATERIAL,")
                .append(" people_yesterday.yesterday_people AS YESTERDAY_PEOPLE, mach_yesterday.yesterday_mach AS YESTERDAY_MACH, method_yesterday.yesterday_method AS YESTERDAY_METHOD, material_yesterday.yesterday_material AS YESTERDAY_METHOD")
                .append(" FROM line")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")

//        people_today
                .append(" LEFT JOIN (SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" WHEN 0  THEN 1 ELSE")
                .append(" (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" END AS today_people, line.id AS line_id")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" WHERE downtime.downtime_cause_category_id = 1")
                .append(" AND date_part('year', actual_start)||'-'||date_part('month', actual_start)||'-'||date_part('day', actual_start) =")
                .append(" date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)")
                .append(whereSQL.toString())
                .append(" GROUP BY line.id, production.last_update, production.actual_start")
                .append(" ) AS people_today ON line.id = people_today.line_id")

//        mach_today
                .append(" LEFT JOIN (SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" WHEN 0  THEN 1 ELSE")
                .append(" (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" END AS today_mach, line.id AS line_id")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" WHERE downtime.downtime_cause_category_id = 2")
                .append(" AND date_part('year', actual_start)||'-'||date_part('month', actual_start)||'-'||date_part('day', actual_start) =")
                .append(" date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)")
                .append(whereSQL.toString())
                .append(" GROUP BY line.id, production.last_update, production.actual_start")
                .append(" ) AS mach_today ON line.id = mach_today.line_id")

//        method_today
                .append(" LEFT JOIN (SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" WHEN 0  THEN 1 ELSE")
                .append(" (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" END AS today_method, line.id AS line_id")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" WHERE downtime.downtime_cause_category_id = 3")
                .append(" AND date_part('year', actual_start)||'-'||date_part('month', actual_start)||'-'||date_part('day', actual_start) =")
                .append(" date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)")
                .append(whereSQL.toString())
                .append(" GROUP BY line.id, production.last_update, production.actual_start")
                .append(" ) AS method_today ON line.id = method_today.line_id")

//        material_today
                .append(" LEFT JOIN (SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" WHEN 0  THEN 1 ELSE")
                .append(" (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" END AS today_material, line.id AS line_id")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" WHERE downtime.downtime_cause_category_id = 4")
                .append(" AND date_part('year', actual_start)||'-'||date_part('month', actual_start)||'-'||date_part('day', actual_start) =")
                .append(" date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)")
                .append(whereSQL.toString())
                .append(" GROUP BY line.id, production.last_update, production.actual_start")
                .append(" ) AS material_today ON line.id = material_today.line_id")

//        people_yesterday
                .append(" LEFT JOIN ( SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS yesterday_people, line.id AS line_id")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN workday ON line.id = workday.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" WHERE downtime.downtime_cause_category_id = 1")
                .append(" AND date_part('year', actual_start) || '-' || date_part('month', actual_start) || '-' || date_part('day', actual_start) =")
                .append(" date_part('year', yesterday) || '-' || date_part('month', yesterday) || '-' || date_part('day', yesterday)")
                .append(whereSQL.toString())
                .append(" GROUP BY line.id, production.actual_finish, production.actual_start")
                .append(" ) people_yesterday ON line.id = people_yesterday.line_id")

//        mach_yesterday
                .append(" LEFT JOIN ( SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS yesterday_mach, line.id AS line_id")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN workday ON line.id = workday.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" WHERE downtime.downtime_cause_category_id = 2")
                .append(" AND date_part('year', actual_start) || '-' || date_part('month', actual_start) || '-' || date_part('day', actual_start) =")
                .append(" date_part('year', yesterday) || '-' || date_part('month', yesterday) || '-' || date_part('day', yesterday)")
                .append(whereSQL.toString())
                .append(" GROUP BY line.id, production.actual_finish, production.actual_start")
                .append(" ) mach_yesterday ON line.id = mach_yesterday.line_id")

//        method_yesterday
                .append(" LEFT JOIN ( SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS yesterday_method, line.id AS line_id")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN workday ON line.id = workday.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" WHERE downtime.downtime_cause_category_id = 3")
                .append(" AND date_part('year', actual_start) || '-' || date_part('month', actual_start) || '-' || date_part('day', actual_start) =")
                .append(" date_part('year', yesterday) || '-' || date_part('month', yesterday) || '-' || date_part('day', yesterday)")
                .append(whereSQL.toString())
                .append(" GROUP BY line.id, production.actual_finish, production.actual_start")
                .append(" ) method_yesterday ON line.id = method_yesterday.line_id")

//        material_yesterday
                .append(" LEFT JOIN ( SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS yesterday_material, line.id AS line_id")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN workday ON line.id = workday.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" WHERE downtime.downtime_cause_category_id = 4")
                .append(" AND date_part('year', actual_start) || '-' || date_part('month', actual_start) || '-' || date_part('day', actual_start) =")
                .append(" date_part('year', yesterday) || '-' || date_part('month', yesterday) || '-' || date_part('day', yesterday)")
                .append(whereSQL.toString())
                .append(" GROUP BY line.id, production.actual_finish, production.actual_start")
                .append(" ) material_yesterday ON line.id = material_yesterday.line_id")

                .append(" WHERE date_part('year', production.actual_start)||'-'||date_part('month', production.actual_start)||'-'||date_part('day', production.actual_start) =")
                .append(" date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)")
                .append(whereSQL.toString())
                .append(" GROUP BY line.code, line.name,")
                .append(" people_today.today_people, mach_today.today_mach, method_today.today_method, material_today.today_material,")
                .append(" people_yesterday.yesterday_people, mach_yesterday.yesterday_mach, method_yesterday.yesterday_method, material_yesterday.yesterday_material")
                .append(" ORDER BY line.code");

        log.debug(" --getBreakDown {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("LINE_CODE", StringType.INSTANCE)
                    .addScalar("TODAY_PEOPLE", BigDecimalType.INSTANCE)
                    .addScalar("TODAY_MACH", BigDecimalType.INSTANCE)
                    .addScalar("TODAY_METHOD", BigDecimalType.INSTANCE)
                    .addScalar("TODAY_MATERIAL", BigDecimalType.INSTANCE)
//                    .addScalar("TODAY_SUMMARY", BigDecimalType.INSTANCE)
                    .addScalar("YESTERDAY_PEOPLE", BigDecimalType.INSTANCE)
                    .addScalar("YESTERDAY_MACH", BigDecimalType.INSTANCE)
                    .addScalar("YESTERDAY_METHOD", BigDecimalType.INSTANCE)
                    .addScalar("YESTERDAY_METHOD", BigDecimalType.INSTANCE);
//                    .addScalar("YESTERDAY_SUMMARY", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            BigDecimal summaryToday;
            BigDecimal summaryYesterDay;

            for (Object[] entity : objects) {
                BreakDownTableView breakDownTableView = new BreakDownTableView();

                breakDownTableView.setLineCode(Utils.parseString(entity[0]));
                breakDownTableView.setToDayPeople(Utils.parseBigDecimal(entity[1]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setToDayMach(Utils.parseBigDecimal(entity[2]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setToDayMethod(Utils.parseBigDecimal(entity[3]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setToDayMaterial(Utils.parseBigDecimal(entity[4]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                summaryToday = breakDownTableView.getToDayPeople().add(breakDownTableView.getToDayMach()).add(breakDownTableView.getToDayMethod()).add(breakDownTableView.getToDayMaterial());

                breakDownTableView.setToDayTotal(summaryToday.setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                breakDownTableView.setYesterDayPeople(Utils.parseBigDecimal(entity[5]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setYesterDayMach(Utils.parseBigDecimal(entity[6]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setYesterDayMethod(Utils.parseBigDecimal(entity[7]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setYesterDayMaterial(Utils.parseBigDecimal(entity[8]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                summaryYesterDay = breakDownTableView.getYesterDayPeople().add(breakDownTableView.getYesterDayMach()).add(breakDownTableView.getYesterDayMethod()).add(breakDownTableView.getYesterDayMaterial());

                breakDownTableView.setYesterDayTotal(summaryYesterDay.setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                // To DAY
                if (Utils.compareLessBigDecimal(breakDownTableView.getToDayPeople(), man)){
                    breakDownTableView.setStylePeopleToday(green);
                } else {
                    breakDownTableView.setStylePeopleToday(red);
                }

                if (Utils.compareLessBigDecimal(breakDownTableView.getToDayMach(), mach)){
                    breakDownTableView.setStyleMachToday(green);
                } else {
                    breakDownTableView.setStyleMachToday(red);
                }

                if (Utils.compareLessBigDecimal(breakDownTableView.getToDayMethod(), method)){
                    breakDownTableView.setStyleMethodToday(green);
                } else {
                    breakDownTableView.setStyleMethodToday(red);
                }

                if (Utils.compareLessBigDecimal(breakDownTableView.getToDayMaterial(), material)){
                    breakDownTableView.setStyleMaterialToday(green);
                } else {
                    breakDownTableView.setStyleMaterialToday(red);
                }

                // YESTERDAY
                if (Utils.compareLessBigDecimal(breakDownTableView.getYesterDayPeople(), man)){
                    breakDownTableView.setStylePeopleYesterday(green);
                } else {
                    breakDownTableView.setStylePeopleYesterday(red);
                }

                if (Utils.compareLessBigDecimal(breakDownTableView.getYesterDayMach(), mach)){
                    breakDownTableView.setStyleMachYesterday(green);
                } else {
                    breakDownTableView.setStyleMachYesterday(red);
                }

                if (Utils.compareLessBigDecimal(breakDownTableView.getYesterDayMethod(), method)){
                    breakDownTableView.setStyleMethodYesterday(green);
                } else {
                    breakDownTableView.setStyleMethodYesterday(red);
                }

                if (Utils.compareLessBigDecimal(breakDownTableView.getYesterDayMaterial(), material)){
                    breakDownTableView.setStyleMaterialYesterday(green);
                } else {
                    breakDownTableView.setStyleMaterialYesterday(red);
                }

                if (Utils.compareLessBigDecimal(breakDownTableView.getToDayTotal(), sum)){
                    breakDownTableView.setStyleTotalToDay(green);
                    breakDownTableView.setTrend(breakDownTableView.getToDayTotal().subtract(breakDownTableView.getYesterDayTotal()).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                } else {
                    breakDownTableView.setStyleTotalToDay(red);
                    breakDownTableView.setTrend(breakDownTableView.getYesterDayTotal().subtract(breakDownTableView.getToDayTotal()).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                }

                if (Utils.compareLessBigDecimal(breakDownTableView.getYesterDayTotal(), sum)){
                    breakDownTableView.setStyleTotalYesterday(green);
                } else {
                    breakDownTableView.setStyleTotalYesterday(red);
                }

                if (Utils.compareLessBigDecimal(breakDownTableView.getTrend(), sum)){
                    breakDownTableView.setImage(up);
                } else {
                    breakDownTableView.setImage(down);
                }

                breakDownTableViewList.add(breakDownTableView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return breakDownTableViewList;
    }
}
