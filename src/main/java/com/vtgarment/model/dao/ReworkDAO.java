package com.vtgarment.model.dao;

import com.vtgarment.model.view.rework.ReworkTableView;
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
public class ReworkDAO extends GenericDAO<String, Integer> {
    private final int twoDecimal = 2;
    @Value("#{config['style.red']}") private String red;
    @Value("#{config['style.green']}") private String green;
    @Value("#{config['arrow.up']}") private String up;
    @Value("#{config['arrow.down']}") private String down;

    public List<ReworkTableView> getRework(int factoryId, int bildingFloorId, String lineId){
        List<ReworkTableView> reworkTableViewList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

//        sql.append(" SELECT today.code||today.name AS CODE_NAME, yesterday.rework_actual AS YESTERDAY, today.rework_actual AS TODAY")
//                .append(" FROM (SELECT line.id AS LINE_ID, line.code, line.name, rework_actual, factory_id, building_floor.id AS FLOOR_ID FROM line")
//                .append(" LEFT JOIN production ON line.id = production.line_id")
//                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
//                .append(" WHERE date_part('year', plan_start) = date_part('year', current_timestamp)")
//                .append(" AND date_part('month', plan_start) = date_part('month', current_timestamp)")
//                .append(" AND date_part('day', plan_start) = date_part('day', current_timestamp)) AS today")
//                .append(" LEFT JOIN (SELECT line_id,rework_actual FROM production")
//                .append(" WHERE (date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start) =")
//                .append(" (SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
//                .append(" FROM production WHERE id = (SELECT max(id) FROM production)))")
//                .append(" AND ((SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
//                .append(" FROM production WHERE id = (SELECT max(id) FROM production)) < date_part('year', current_timestamp) || '-' || date_part('month', current_timestamp) ||")
//                .append(" '-' ||date_part('day', current_timestamp))) AS Yesterday ON today.LINE_ID = yesterday.line_id WHERE 1 = 1");

        sql.append(" SELECT rework_today.code||rework_today.name AS CODE_NAME, rework_yesterday.rework_actual AS YESTERDAY, rework_today.rework_actual AS TODAY, rework_today.rework_target AS TARGET")
                .append(" FROM line")
                .append(" INNER JOIN (")
                .append(" SELECT line.id, code, name, rework_actual, rework_target")
                .append(" FROM line")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" WHERE date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" = date_part('year', current_timestamp) || '-' || date_part('month', current_timestamp) || '-' || date_part('day', current_timestamp)")
                .append(" ) AS rework_today ON line.id = rework_today.id")
                .append(" INNER JOIN(")
                .append(" SELECT production.line_id, rework_actual")
                .append(" FROM production")
                .append(" LEFT JOIN workday ON production.line_id = workday.line_id")
                .append(" WHERE date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" = date_part('year', yesterday) || '-' || date_part('month', yesterday) || '-' || date_part('day', yesterday)")
                .append(" ) AS rework_yesterday ON line.id = rework_yesterday.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" LEFT JOIN user_line ON line.id = user_line.line_id")
                .append(" WHERE 1=1");

        if (!Utils.isZero(factoryId)){
            sql.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(bildingFloorId)){
            sql.append(" AND building_floor.id =").append(bildingFloorId);
        }

        if (!Utils.isNull(lineId) && !Utils.isEmpty(lineId) && !"0".equals(lineId)){
            sql.append(" AND production.line_id in (").append(lineId).append(")");
        }


        sql.append(" GROUP BY rework_today.code, rework_today.name, rework_yesterday.rework_actual, rework_today.rework_actual, rework_today.rework_target");
        sql.append(" ORDER BY rework_today.code");

        log.debug("getRework : {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("CODE_NAME", StringType.INSTANCE)
                    .addScalar("YESTERDAY", BigDecimalType.INSTANCE)
                    .addScalar("TODAY", BigDecimalType.INSTANCE)
                    .addScalar("TARGET", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                ReworkTableView reworkTableView = new ReworkTableView();

                reworkTableView.setLineCode(Utils.parseString(entity[0]));
                reworkTableView.setYesterDay(Utils.parseBigDecimal(entity[1]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                reworkTableView.setToDay(Utils.parseBigDecimal(entity[2]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                reworkTableView.setTarget(Utils.parseBigDecimal(entity[3]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                if (Utils.compareLessBigDecimal(reworkTableView.getToDay(), reworkTableView.getTarget())){
                    reworkTableView.setStyleToDay(green);
                } else {
                    reworkTableView.setStyleToDay(red);
                }

                if (Utils.compareLessBigDecimal(reworkTableView.getYesterDay(), reworkTableView.getTarget())){
                    reworkTableView.setStyleYesterDay(green);
                } else {
                    reworkTableView.setStyleYesterDay(red);
                }

                if (Utils.compareLessBigDecimal(reworkTableView.getToDay(), reworkTableView.getYesterDay())){
                    reworkTableView.setTrend(reworkTableView.getYesterDay().subtract(reworkTableView.getToDay()).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                    reworkTableView.setImage(up);
                } else {
                    reworkTableView.setTrend(reworkTableView.getToDay().subtract(reworkTableView.getYesterDay()).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                    reworkTableView.setImage(down);
                }

                reworkTableViewList.add(reworkTableView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return reworkTableViewList;
    }
}
