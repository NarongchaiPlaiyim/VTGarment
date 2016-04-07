package com.vtgarment.model.dao;

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

    public List<BreakDownTableView> getBreakDown(int factoryId, int bildingFloorId, int lineId, int leaderId){
        List<BreakDownTableView> breakDownTableViewList = new ArrayList<>();

        StringBuilder sql = new StringBuilder();

//        Select ALL
        sql.append(" SELECT line.code || line.NAME AS CODE_NAME,")
                .append(" PEOPLE_TODAY.TODAY_PEOPLE, MACH_TODAY.TODAY_MACH, METHOD_TODAY.TODAY_METHOD, MATERIAL_TODAY.TODAY_MATERIAL,")
                .append(" PEOPLE_YESTERDAY.YESTERDAY_PEOPLE, MACH_YESTERDAY.YESTERDAY_MACH, METHOD_YESTERDAY.YESTERDAY_METHOD, METERIAL_YESTERDAY.YESTERDAY_METERIAL")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.ID = downtime.line_id")
                .append(" LEFT JOIN production ON line.ID = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID");

//        PROPLE TODAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" WHEN 0  THEN 1")
                .append(" ELSE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" END AS today_people, line.id AS line_id")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" WHERE downtime.downtime_cause_category_id = 1")
                .append(" AND date_part('year', from_datetime)||'-'||date_part('month', from_datetime)||'-'||date_part('day', from_datetime)")
                .append(" = date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)")
                .append(" GROUP BY downtime.to_datetime, downtime.from_datetime, line.id")
                .append(" ) AS people_today ON line.id = people_today.line_id");

//        MACH TODAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" WHEN 0  THEN 1")
                .append(" ELSE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" END AS TODAY_MACH, line.id AS line_id")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" WHERE downtime.downtime_cause_category_id = 2")
                .append(" AND date_part('year', from_datetime)||'-'||date_part('month', from_datetime)||'-'||date_part('day', from_datetime)")
                .append(" = date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)")
                .append(" GROUP BY downtime.to_datetime, downtime.from_datetime, line.id")
                .append(" ) AS MACH_TODAY ON line.id = MACH_TODAY.line_id");

//        METHOD TODAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" WHEN 0  THEN 1")
                .append(" ELSE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" END AS TODAY_METHOD, line.id AS line_id")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" WHERE downtime.downtime_cause_category_id = 3")
                .append(" AND date_part('year', from_datetime)||'-'||date_part('month', from_datetime)||'-'||date_part('day', from_datetime)")
                .append(" = date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)")
                .append(" GROUP BY downtime.to_datetime, downtime.from_datetime, line.id")
                .append(" ) AS METHOD_TODAY ON line.id = METHOD_TODAY.line_id");

//        MATERIAL TODAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" WHEN 0  THEN 1")
                .append(" ELSE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" END AS TODAY_MATERIAL, line.id AS line_id")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" WHERE downtime.downtime_cause_category_id = 4")
                .append(" AND date_part('year', from_datetime)||'-'||date_part('month', from_datetime)||'-'||date_part('day', from_datetime)")
                .append(" = date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)")
                .append(" GROUP BY downtime.to_datetime, downtime.from_datetime, line.id")
                .append(" ) AS MATERIAL_TODAY ON line.id = MATERIAL_TODAY.line_id");

//        SUMMARY TODAY
//        sql.append(" LEFT JOIN (")
//                .append(" SELECT (sum(total_downtime) * 100) /")
//                .append(" CASE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
//                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
//                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
//                .append(" WHEN 0  THEN 1")
//                .append(" ELSE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
//                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
//                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
//                .append(" END AS TODAY_SUMMARY, line.id AS line_id")
//                .append(" FROM line")
//                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
//                .append(" WHERE date_part('year', from_datetime)||'-'||date_part('month', from_datetime)||'-'||date_part('day', from_datetime)")
//                .append(" = date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)")
//                .append(" GROUP BY downtime.to_datetime, downtime.from_datetime, line.id")
//                .append(" ) AS SUMMARY_TODAY ON line.id = SUMMARY_TODAY.line_id");

//        PROPLE YESTERDAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" WHEN 0 THEN 1")
                .append(" ELSE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" END AS yesterday_people, downtime.line_id AS line_id")
                .append(" FROM downtime")
                .append(" LEFT JOIN workday ON downtime.line_id = workday.line_id")
                .append(" WHERE downtime.downtime_cause_category_id = 1")
                .append(" AND date_part('year', from_datetime) || '-' || date_part('month', from_datetime) || '-' || date_part('day', from_datetime)")
                .append(" = date_part('year', yesterday) || '-' || date_part('month', yesterday) || '-' || date_part('day', yesterday)")
                .append(" GROUP BY downtime.to_datetime, downtime.from_datetime, downtime.line_id")
                .append(" ) people_yesterday ON line.id = people_yesterday.line_id");

//        MACH_YESTERDAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" WHEN 0 THEN 1")
                .append(" ELSE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" END AS YESTERDAY_MACH, downtime.line_id AS line_id")
                .append(" FROM downtime")
                .append(" LEFT JOIN workday ON downtime.line_id = workday.line_id")
                .append(" WHERE downtime.downtime_cause_category_id = 2")
                .append(" AND date_part('year', from_datetime) || '-' || date_part('month', from_datetime) || '-' || date_part('day', from_datetime)")
                .append(" = date_part('year', yesterday) || '-' || date_part('month', yesterday) || '-' || date_part('day', yesterday)")
                .append(" GROUP BY downtime.to_datetime, downtime.from_datetime, downtime.line_id")
                .append(" ) MACH_YESTERDAY ON line.id = MACH_YESTERDAY.line_id");

//        METHOD YESTERDAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" WHEN 0 THEN 1")
                .append(" ELSE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" END AS YESTERDAY_METHOD, downtime.line_id AS line_id")
                .append(" FROM downtime")
                .append(" LEFT JOIN workday ON downtime.line_id = workday.line_id")
                .append(" WHERE downtime.downtime_cause_category_id = 3")
                .append(" AND date_part('year', from_datetime) || '-' || date_part('month', from_datetime) || '-' || date_part('day', from_datetime)")
                .append(" = date_part('year', yesterday) || '-' || date_part('month', yesterday) || '-' || date_part('day', yesterday)")
                .append(" GROUP BY downtime.to_datetime, downtime.from_datetime, downtime.line_id")
                .append(" ) METHOD_YESTERDAY ON line.id = METHOD_YESTERDAY.line_id");

//        MATERIAL YESTERDAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" WHEN 0 THEN 1")
                .append(" ELSE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
                .append(" END AS YESTERDAY_METERIAL, downtime.line_id AS line_id")
                .append(" FROM downtime")
                .append(" LEFT JOIN workday ON downtime.line_id = workday.line_id")
                .append(" WHERE downtime.downtime_cause_category_id = 4")
                .append(" AND date_part('year', from_datetime) || '-' || date_part('month', from_datetime) || '-' || date_part('day', from_datetime)")
                .append(" = date_part('year', yesterday) || '-' || date_part('month', yesterday) || '-' || date_part('day', yesterday)")
                .append(" GROUP BY downtime.to_datetime, downtime.from_datetime, downtime.line_id")
                .append(" ) METERIAL_YESTERDAY ON line.id = METERIAL_YESTERDAY.line_id");

//        SUMMARY_YESTERDAY
//        sql.append(" LEFT JOIN (")
//                .append(" SELECT (sum(total_downtime) * 100) /")
//                .append(" CASE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
//                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
//                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
//                .append(" WHEN 0 THEN 1")
//                .append(" ELSE (date_part('hour', downtime.to_datetime - downtime.from_datetime) * 3600) +")
//                .append(" (date_part('min', downtime.to_datetime - downtime.from_datetime) * 60) +")
//                .append(" date_part('sec', downtime.to_datetime - downtime.from_datetime)")
//                .append(" END AS YESTERDAY_SUMMARY, downtime.line_id AS line_id")
//                .append(" FROM downtime")
//                .append(" LEFT JOIN workday ON downtime.line_id = workday.line_id")
//                .append(" WHERE date_part('year', from_datetime) || '-' || date_part('month', from_datetime) || '-' || date_part('day', from_datetime)")
//                .append(" = date_part('year', yesterday) || '-' || date_part('month', yesterday) || '-' || date_part('day', yesterday)")
//                .append(" GROUP BY downtime.to_datetime, downtime.from_datetime, downtime.line_id")
//                .append(" ) SUMMARY_YESTERDAY ON line.id = SUMMARY_YESTERDAY.line_id");

        sql.append(" WHERE date_part('year', downtime.from_datetime)||'-'||date_part('month', downtime.from_datetime)||'-'||date_part('day', downtime.from_datetime)")
                .append(" = date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)");

        if (!Utils.isZero(factoryId)){
            sql.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(bildingFloorId)){
            sql.append(" AND building_floor.id =").append(bildingFloorId);
        }

        if (!Utils.isZero(lineId)){
            sql.append(" AND line.id =").append(lineId);
        }

        if (!Utils.isZero(leaderId)){
            sql.append(" AND line.leader_id =").append(leaderId);
        }

        sql.append(" GROUP BY line. ID, line.code, line.NAME,")
                .append(" PEOPLE_TODAY.TODAY_PEOPLE, MACH_TODAY.TODAY_MACH, METHOD_TODAY.TODAY_METHOD, MATERIAL_TODAY.TODAY_MATERIAL,")
                .append(" PEOPLE_YESTERDAY.YESTERDAY_PEOPLE, MACH_YESTERDAY.YESTERDAY_MACH, METHOD_YESTERDAY.YESTERDAY_METHOD, METERIAL_YESTERDAY.YESTERDAY_METERIAL");

        log.debug(" --getBreakDown {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("CODE_NAME", StringType.INSTANCE)
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

                if (Utils.compareBigDecimal(breakDownTableView.getToDayPeople(), breakDownTableView.getYesterDayPeople())){
                    breakDownTableView.setStylePeopleToday(red);
                    breakDownTableView.setStylePeopleYesterday(green);
                } else {
                    breakDownTableView.setStylePeopleToday(green);
                    breakDownTableView.setStylePeopleYesterday(red);
                }

                if (Utils.compareBigDecimal(breakDownTableView.getToDayMach(), breakDownTableView.getYesterDayMach())){
                    breakDownTableView.setStyleMachToday(red);
                    breakDownTableView.setStyleMachYesterday(green);
                } else {
                    breakDownTableView.setStyleMachToday(green);
                    breakDownTableView.setStyleMachYesterday(red);
                }

                if (Utils.compareBigDecimal(breakDownTableView.getToDayMethod(), breakDownTableView.getYesterDayMethod())){
                    breakDownTableView.setStyleMethodToday(red);
                    breakDownTableView.setStyleMethodYesterday(green);
                } else {
                    breakDownTableView.setStyleMethodToday(green);
                    breakDownTableView.setStyleMethodYesterday(red);
                }

                if (Utils.compareBigDecimal(breakDownTableView.getToDayMaterial(), breakDownTableView.getToDayMaterial())){
                    breakDownTableView.setStyleMaterialToday(red);
                    breakDownTableView.setStyleMaterialYesterday(green);
                } else {
                    breakDownTableView.setStyleMaterialToday(green);
                    breakDownTableView.setStyleMaterialYesterday(red);
                }

                if (Utils.compareBigDecimal(breakDownTableView.getToDayTotal(), breakDownTableView.getYesterDayTotal())){
                    breakDownTableView.setStyleTotalToDay(red);
                    breakDownTableView.setStyleTotalYesterday(green);
                    breakDownTableView.setTrend(breakDownTableView.getToDayTotal().subtract(breakDownTableView.getYesterDayTotal()).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                    breakDownTableView.setImage(down);
                } else {
                    breakDownTableView.setStyleTotalToDay(green);
                    breakDownTableView.setStyleTotalYesterday(red);
                    breakDownTableView.setTrend(breakDownTableView.getYesterDayTotal().subtract(breakDownTableView.getToDayTotal()).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                    breakDownTableView.setImage(up);
                }

                breakDownTableViewList.add(breakDownTableView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return breakDownTableViewList;
    }
}
