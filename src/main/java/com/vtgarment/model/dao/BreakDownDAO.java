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

    public List<BreakDownTableView> getBreakDown(int factoryId, int bildingFloorId, int lineId){
        List<BreakDownTableView> breakDownTableViewList = new ArrayList<>();

//        StringBuilder sql = new StringBuilder();
//
//        StringBuilder selectAll = new StringBuilder();
//        StringBuilder groupBySelectAll = new StringBuilder();
//
//        StringBuilder toDay = new StringBuilder();
//        StringBuilder groupByToDay = new StringBuilder();
//
//        StringBuilder yesyerDay = new StringBuilder();
//        StringBuilder whereYesterDay = new StringBuilder();
//        StringBuilder groupByYesterDay = new StringBuilder();
//
//        StringBuilder where = new StringBuilder();
//
//        where.append(" WHERE date_part('year', plan_start) = date_part('year', current_timestamp)")
//                .append(" AND date_part('month', plan_start) = date_part('month', current_timestamp)")
//                .append(" AND date_part('day', plan_start) = date_part('day', current_timestamp)");
//
//        selectAll.append(" SELECT line.id AS LINE_ID, line.code||line.name AS LINE_CODE, ")
//                .append(" PEOPLE_TODAY.TODAY AS TODAY_PEOPLE, MACH_TODAY.TODAY AS TODAY_MACH, METHOD_TODAY.TODAY AS TODAY_METHOD, MATERIAL_TODAY.TODAY AS TODAY_MATERIAL,SUMMARY_TODAY.TODAY AS TODAY_SUMMARY,")
//                .append(" PEOPLE_YESTERDAY.YESTERDAY AS YESTERDAY_PEOPLE, MACH_YESTERDAY.YESTERDAY AS YESTERDAY_MACH, METHOD_YESTERDAY.YESTERDAY AS YESTERDAY_METHOD,")
//                .append("  METERIAL_YESTERDAY.YESTERDAY AS YESTERDAY_MATERIAL, SUMMARY_YESTERDAY.YESTERDAY AS YESTERDAY_SUMMARY")
//                .append(" FROM line")
//                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
//                .append(" LEFT JOIN production ON line.id = production.line_id")
//                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id");
//
//        groupBySelectAll.append(" GROUP BY line.id,  code, line.name,")
//                .append(" PEOPLE_TODAY.TODAY, MACH_TODAY.TODAY, METHOD_TODAY.TODAY, MATERIAL_TODAY.TODAY, SUMMARY_TODAY.TODAY,")
//                .append(" PEOPLE_YESTERDAY.YESTERDAY, MACH_YESTERDAY.YESTERDAY, METHOD_YESTERDAY.YESTERDAY, METERIAL_YESTERDAY.YESTERDAY, SUMMARY_YESTERDAY.YESTERDAY");
//
//        toDay.append(" LEFT JOIN ( SELECT (sum(total_downtime) * 100) /")
//                .append(" CASE (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
//                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
//                .append(" date_part('sec', production.actual_finish - production.actual_start)")
//                .append(" WHEN 0 THEN 1")
//                .append(" ELSE (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
//                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
//                .append(" date_part('sec', production.actual_finish - production.actual_start)")
//                .append(" END AS TODAY, line.id AS LINE_ID")
//                .append(" FROM line")
//                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
//                .append(" LEFT JOIN production ON line.id = production.line_id");
//
//        groupByToDay.append(" GROUP BY production.actual_finish, production.actual_start, line.id");
//
//        yesyerDay.append(" LEFT JOIN ( SELECT (sum(total_downtime) * 100) /")
//                .append(" CASE (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
//                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
//                .append(" date_part('sec', production.actual_finish - production.actual_start)")
//                .append(" WHEN 0 THEN 1")
//                .append(" ELSE (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
//                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
//                .append(" date_part('sec', production.actual_finish - production.actual_start)")
//                .append(" END AS YESTERDAY, downtime.line_id AS LINE_ID")
//                .append(" FROM downtime")
//                .append(" LEFT JOIN production ON downtime.production_id = production.id");
//
//        whereYesterDay.append(" WHERE (date_part('year', to_datetime) || '-' || date_part('month', to_datetime) || '-' || date_part('day', to_datetime) =")
//                .append(" (SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
//                .append(" FROM production WHERE id = (SELECT max(id) FROM production)))")
//                .append(" AND ((SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start) FROM production")
//                .append(" WHERE id = (SELECT max(id)FROM production)) < date_part('year', current_timestamp) || '-' || date_part('month', current_timestamp) || '-' || date_part('day', current_timestamp))");
//
//        groupByYesterDay.append(" GROUP BY production.actual_finish, production.actual_start, downtime.line_id");
//
//        sql.append(selectAll);
//            for (int i=1;i<5;i++){
//                sql.append(toDay).append(where).append(" AND downtime.downtime_cause_category_id = ").append(i).append(groupByToDay);
//
//                switch (i){
//                    case 1: sql.append(" ) PEOPLE_TODAY ON line.id = PEOPLE_TODAY.LINE_ID"); break;
//                    case 2: sql.append(" ) MACH_TODAY ON line.id = MACH_TODAY.LINE_ID"); break;
//                    case 3: sql.append(" ) METHOD_TODAY ON line.id = METHOD_TODAY.LINE_ID"); break;
//                    case 4: sql.append(" ) MATERIAL_TODAY ON line.id = MATERIAL_TODAY.LINE_ID"); break;
//                }
//            }
//
//        log.debug("--toDay {}", sql.toString());
//
//        sql.append(toDay).append(where).append(groupByToDay).append(" ) SUMMARY_TODAY ON line.id = SUMMARY_TODAY.LINE_ID");
//
//        for (int i=1;i<5;i++){
//            sql.append(yesyerDay).append(whereYesterDay).append(" AND downtime.downtime_cause_category_id = ").append(i).append(groupByYesterDay);
//
//            switch (i){
//                case 1: sql.append(" ) PEOPLE_YESTERDAY ON line.id = PEOPLE_YESTERDAY.LINE_ID"); break;
//                case 2: sql.append(" ) MACH_YESTERDAY ON line.id = MACH_YESTERDAY.LINE_ID"); break;
//                case 3: sql.append(" ) METHOD_YESTERDAY ON line.id = METHOD_YESTERDAY.LINE_ID"); break;
//                case 4: sql.append(" ) METERIAL_YESTERDAY ON line.id = METERIAL_YESTERDAY.LINE_ID"); break;
//            }
//        }

//        log.debug("--YesterDay {}", sql.toString());
//
//        sql.append(yesyerDay).append(whereYesterDay).append(groupByToDay).append(" ) SUMMARY_YESTERDAY ON line.id = SUMMARY_YESTERDAY.LINE_ID")
//            .append(groupByYesterDay).append(where);
//
//        if (!Utils.isZero(factoryId)){
//            sql.append(" AND building_floor.factory_id =").append(factoryId);
//        }
//
//        if (!Utils.isZero(bildingFloorId)){
//            sql.append(" AND building_floor.id =").append(bildingFloorId);
//        }
//
//        if (!Utils.isZero(lineId)){
//            sql.append(" AND line.LINE_ID =").append(lineId);
//        }
//
//        sql.append(groupBySelectAll);
//
//        log.debug(" --getBreakDown {}", sql.toString());

        StringBuilder sql = new StringBuilder();

//        Select ALL
        sql.append(" SELECT line.code || line.NAME AS LINE_CODE,")
                .append(" PEOPLE_TODAY.TODAY_PEOPLE, MACH_TODAY.TODAY_MACH, METHOD_TODAY.TODAY_METHOD, MATERIAL_TODAY.TODAY_MATERIAL, SUMMARY_TODAY.TODAY_SUMMARY,")
                .append(" PEOPLE_YESTERDAY.YESTERDAY_PEOPLE, MACH_YESTERDAY.YESTERDAY_MACH, METHOD_YESTERDAY.YESTERDAY_METHOD, METERIAL_YESTERDAY.YESTERDAY_MATERIAL, SUMMARY_YESTERDAY.YESTERDAY_SUMMARY")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.ID = downtime.line_id")
                .append(" LEFT JOIN production ON line.ID = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID");

//        PROPLE TODAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT(SUM(total_downtime) * 100) /")
                .append(" CASE ( date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS TODAY_PEOPLE, line.ID AS LINE_ID")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line. ID = downtime.line_id")
                .append(" LEFT JOIN production ON line. ID = production.line_id")
                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(" AND downtime.downtime_cause_category_id = 1")
                .append(" GROUP BY production.actual_finish, production.actual_start, line.ID")
                .append(" ) PEOPLE_TODAY ON line. ID = PEOPLE_TODAY.LINE_ID");

        //        MACH TODAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT(SUM(total_downtime) * 100) /")
                .append(" CASE ( date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS TODAY_MACH, line.ID AS LINE_ID")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line. ID = downtime.line_id")
                .append(" LEFT JOIN production ON line. ID = production.line_id")
                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(" AND downtime.downtime_cause_category_id = 2")
                .append(" GROUP BY production.actual_finish, production.actual_start, line.ID")
                .append(" ) MACH_TODAY ON line. ID = MACH_TODAY.LINE_ID");

        //        METHOD TODAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT(SUM(total_downtime) * 100) /")
                .append(" CASE ( date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS TODAY_METHOD, line.ID AS LINE_ID")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line. ID = downtime.line_id")
                .append(" LEFT JOIN production ON line. ID = production.line_id")
                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(" AND downtime.downtime_cause_category_id = 3")
                .append(" GROUP BY production.actual_finish, production.actual_start, line.ID")
                .append(" ) METHOD_TODAY ON line. ID = METHOD_TODAY.LINE_ID");

        //        MATERIAL TODAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT(SUM(total_downtime) * 100) /")
                .append(" CASE ( date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS TODAY_MATERIAL, line.ID AS LINE_ID")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line. ID = downtime.line_id")
                .append(" LEFT JOIN production ON line. ID = production.line_id")
                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(" AND downtime.downtime_cause_category_id = 4")
                .append(" GROUP BY production.actual_finish, production.actual_start, line.ID")
                .append(" ) MATERIAL_TODAY ON line. ID = MATERIAL_TODAY.LINE_ID");

        //        SUMMARY TODAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT(SUM(total_downtime) * 100) /")
                .append(" CASE ( date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS TODAY_SUMMARY, line.ID AS LINE_ID")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line. ID = downtime.line_id")
                .append(" LEFT JOIN production ON line. ID = production.line_id")
                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(" GROUP BY production.actual_finish, production.actual_start, line.ID")
                .append(" ) SUMMARY_TODAY ON line. ID = SUMMARY_TODAY.LINE_ID");

//        PROPLE YESTERDAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT (SUM(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour',production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS YESTERDAY_PEOPLE, downtime.line_id AS LINE_ID")
                .append(" FROM downtime")
                .append(" LEFT JOIN production ON downtime.production_id = production.ID")
                .append(" WHERE (date_part('year', to_datetime) || '-' || date_part('month', to_datetime) || '-' || date_part('day', to_datetime) =")
                .append(" (SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" FROM production")
                .append(" WHERE ID = (SELECT MAX (ID) FROM production)))")
                .append(" AND ((SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" FROM production")
                .append(" WHERE ID = (SELECT MAX (ID) FROM production)) < date_part('year', CURRENT_TIMESTAMP) || '-' ||")
                .append(" date_part('month', CURRENT_TIMESTAMP) || '-' || date_part('day', CURRENT_TIMESTAMP))")
                .append(" AND downtime.downtime_cause_category_id = 1")
                .append(" GROUP BY production.actual_finish, production.actual_start, downtime.line_id")
                .append(" ) PEOPLE_YESTERDAY ON line. ID = PEOPLE_YESTERDAY.LINE_ID");

//        MACH_YESTERDAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT (SUM(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour',production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS YESTERDAY_MACH, downtime.line_id AS LINE_ID")
                .append(" FROM downtime")
                .append(" LEFT JOIN production ON downtime.production_id = production.ID")
                .append(" WHERE (date_part('year', to_datetime) || '-' || date_part('month', to_datetime) || '-' || date_part('day', to_datetime) =")
                .append(" (SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" FROM production")
                .append(" WHERE ID = (SELECT MAX (ID) FROM production)))")
                .append(" AND ((SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" FROM production")
                .append(" WHERE ID = (SELECT MAX (ID) FROM production)) < date_part('year', CURRENT_TIMESTAMP) || '-' ||")
                .append(" date_part('month', CURRENT_TIMESTAMP) || '-' || date_part('day', CURRENT_TIMESTAMP))")
                .append(" AND downtime.downtime_cause_category_id = 2")
                .append(" GROUP BY production.actual_finish, production.actual_start, downtime.line_id")
                .append(" ) MACH_YESTERDAY ON line. ID = MACH_YESTERDAY.LINE_ID");

        //        METHOD YESTERDAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT (SUM(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour',production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS YESTERDAY_METHOD, downtime.line_id AS LINE_ID")
                .append(" FROM downtime")
                .append(" LEFT JOIN production ON downtime.production_id = production.ID")
                .append(" WHERE (date_part('year', to_datetime) || '-' || date_part('month', to_datetime) || '-' || date_part('day', to_datetime) =")
                .append(" (SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" FROM production")
                .append(" WHERE ID = (SELECT MAX (ID) FROM production)))")
                .append(" AND ((SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" FROM production")
                .append(" WHERE ID = (SELECT MAX (ID) FROM production)) < date_part('year', CURRENT_TIMESTAMP) || '-' ||")
                .append(" date_part('month', CURRENT_TIMESTAMP) || '-' || date_part('day', CURRENT_TIMESTAMP))")
                .append(" AND downtime.downtime_cause_category_id = 3")
                .append(" GROUP BY production.actual_finish, production.actual_start, downtime.line_id")
                .append(" ) METHOD_YESTERDAY ON line. ID = METHOD_YESTERDAY.LINE_ID");

        //        MATERIAL YESTERDAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT (SUM(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour',production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS YESTERDAY_MATERIAL, downtime.line_id AS LINE_ID")
                .append(" FROM downtime")
                .append(" LEFT JOIN production ON downtime.production_id = production.ID")
                .append(" WHERE (date_part('year', to_datetime) || '-' || date_part('month', to_datetime) || '-' || date_part('day', to_datetime) =")
                .append(" (SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" FROM production")
                .append(" WHERE ID = (SELECT MAX (ID) FROM production)))")
                .append(" AND ((SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" FROM production")
                .append(" WHERE ID = (SELECT MAX (ID) FROM production)) < date_part('year', CURRENT_TIMESTAMP) || '-' ||")
                .append(" date_part('month', CURRENT_TIMESTAMP) || '-' || date_part('day', CURRENT_TIMESTAMP))")
                .append(" AND downtime.downtime_cause_category_id = 4")
                .append(" GROUP BY production.actual_finish, production.actual_start, downtime.line_id")
                .append(" ) METERIAL_YESTERDAY ON line. ID = METERIAL_YESTERDAY.LINE_ID");

        //        MATERIAL YESTERDAY
        sql.append(" LEFT JOIN (")
                .append(" SELECT (SUM(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" WHEN 0 THEN 1 ELSE")
                .append(" (date_part('hour',production.actual_finish - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.actual_finish - production.actual_start) * 60) +")
                .append(" date_part('sec', production.actual_finish - production.actual_start)")
                .append(" END AS YESTERDAY_SUMMARY, downtime.line_id AS LINE_ID")
                .append(" FROM downtime")
                .append(" LEFT JOIN production ON downtime.production_id = production.ID")
                .append(" WHERE (date_part('year', to_datetime) || '-' || date_part('month', to_datetime) || '-' || date_part('day', to_datetime) =")
                .append(" (SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" FROM production")
                .append(" WHERE ID = (SELECT MAX (ID) FROM production)))")
                .append(" AND ((SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" FROM production")
                .append(" WHERE ID = (SELECT MAX (ID) FROM production)) < date_part('year', CURRENT_TIMESTAMP) || '-' ||")
                .append(" date_part('month', CURRENT_TIMESTAMP) || '-' || date_part('day', CURRENT_TIMESTAMP))")
                .append(" GROUP BY production.actual_finish, production.actual_start, downtime.line_id")
                .append(" ) SUMMARY_YESTERDAY ON line. ID = SUMMARY_YESTERDAY.LINE_ID");

        sql.append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)");

        if (!Utils.isZero(factoryId)){
            sql.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(bildingFloorId)){
            sql.append(" AND building_floor.id =").append(bildingFloorId);
        }

        if (!Utils.isZero(lineId)){
            sql.append(" AND line.id =").append(lineId);
        }

        sql.append(" GROUP BY line. ID, line.code, line.NAME,")
                .append(" PEOPLE_TODAY.TODAY_PEOPLE, MACH_TODAY.TODAY_MACH, METHOD_TODAY.TODAY_METHOD, MATERIAL_TODAY.TODAY_MATERIAL, SUMMARY_TODAY.TODAY_SUMMARY,")
                .append(" PEOPLE_YESTERDAY.YESTERDAY_PEOPLE, MACH_YESTERDAY.YESTERDAY_MACH, METHOD_YESTERDAY.YESTERDAY_METHOD, METERIAL_YESTERDAY.YESTERDAY_MATERIAL, SUMMARY_YESTERDAY.YESTERDAY_SUMMARY");

        log.debug(" --getBreakDown {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("LINE_CODE", StringType.INSTANCE)
                    .addScalar("TODAY_PEOPLE", BigDecimalType.INSTANCE)
                    .addScalar("TODAY_MACH", BigDecimalType.INSTANCE)
                    .addScalar("TODAY_METHOD", BigDecimalType.INSTANCE)
                    .addScalar("TODAY_MATERIAL", BigDecimalType.INSTANCE)
                    .addScalar("TODAY_SUMMARY", BigDecimalType.INSTANCE)
                    .addScalar("YESTERDAY_PEOPLE", BigDecimalType.INSTANCE)
                    .addScalar("YESTERDAY_MACH", BigDecimalType.INSTANCE)
                    .addScalar("YESTERDAY_METHOD", BigDecimalType.INSTANCE)
                    .addScalar("YESTERDAY_MATERIAL", BigDecimalType.INSTANCE)
                    .addScalar("YESTERDAY_SUMMARY", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                BreakDownTableView breakDownTableView = new BreakDownTableView();

                breakDownTableView.setLineCode(Utils.parseString(entity[0]));
                breakDownTableView.setToDayPeople(Utils.parseBigDecimal(entity[1]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setToDayMach(Utils.parseBigDecimal(entity[2]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setToDayMethod(Utils.parseBigDecimal(entity[3]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setToDayMaterial(Utils.parseBigDecimal(entity[4]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setToDayTotal(Utils.parseBigDecimal(entity[5]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                breakDownTableView.setYesterDayPeople(Utils.parseBigDecimal(entity[6]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setYesterDayMach(Utils.parseBigDecimal(entity[7]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setYesterDayMethod(Utils.parseBigDecimal(entity[8]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setYesterDayMaterial(Utils.parseBigDecimal(entity[9]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownTableView.setYesterDayTotal(Utils.parseBigDecimal(entity[10]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

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
