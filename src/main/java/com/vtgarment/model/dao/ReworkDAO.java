package com.vtgarment.model.dao;

import com.vtgarment.model.view.rework.ReworkTableView;
import com.vtgarment.utils.Utils;
import org.hibernate.SQLQuery;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

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

    public List<ReworkTableView> getRework(int factoryId, int bildingFloorId, int lineId){
        List<ReworkTableView> reworkTableViewList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT today.code||today.name AS CODE_NAME, yesterday.rework_actual AS YESTERDAY, today.rework_actual AS TODAY")
                .append(" FROM (SELECT line.id AS LINE_ID, line.code, line.name, rework_actual, factory_id, building_floor.id AS FLOOR_ID FROM line")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" WHERE date_part('year', plan_start) = date_part('year', current_timestamp)")
                .append(" AND date_part('month', plan_start) = date_part('month', current_timestamp)")
                .append(" AND date_part('day', plan_start) = date_part('day', current_timestamp)) AS today")
                .append(" LEFT JOIN (SELECT line_id,rework_actual FROM production")
                .append(" WHERE (date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start) =")
                .append(" (SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" FROM production WHERE id = (SELECT max(id) FROM production)))")
                .append(" AND ((SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" FROM production WHERE id = (SELECT max(id) FROM production)) < date_part('year', current_timestamp) || '-' || date_part('month', current_timestamp) ||")
                .append(" '-' ||date_part('day', current_timestamp))) AS Yesterday ON today.LINE_ID = yesterday.line_id WHERE 1 = 1");

        if (!Utils.isZero(factoryId)){
            sql.append(" AND today.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(bildingFloorId)){
            sql.append(" AND today.FLOOR_ID =").append(bildingFloorId);
        }

        if (!Utils.isZero(lineId)){
            sql.append(" AND today.LINE_ID =").append(lineId);
        }

        sql.append(" GROUP BY today.code, today.name, yesterday.rework_actual, today.rework_actual");

        log.debug("getRework : {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("CODE_NAME", StringType.INSTANCE)
                    .addScalar("YESTERDAY", BigDecimalType.INSTANCE)
                    .addScalar("TODAY", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                ReworkTableView reworkTableView = new ReworkTableView();

                reworkTableView.setLineCode(Utils.parseString(entity[0]));
                reworkTableView.setYesterDay(Utils.parseBigDecimal(entity[1]));
                reworkTableView.setToDay(Utils.parseBigDecimal(entity[2]));

                if (Utils.compareBigDecimal(reworkTableView.getToDay(), reworkTableView.getYesterDay())){
                    reworkTableView.setTrend(reworkTableView.getToDay().subtract(reworkTableView.getYesterDay()));
                    reworkTableView.setStyleToDay(red);
                    reworkTableView.setStyleYesterDay(green);
                    reworkTableView.setImage(down);
                } else {
                    reworkTableView.setTrend(reworkTableView.getYesterDay().subtract(reworkTableView.getToDay()));
                    reworkTableView.setStyleToDay(green);
                    reworkTableView.setStyleYesterDay(red);
                    reworkTableView.setImage(up);
                }

                reworkTableViewList.add(reworkTableView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return reworkTableViewList;
    }
}
