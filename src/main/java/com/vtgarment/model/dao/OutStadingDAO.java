package com.vtgarment.model.dao;

import com.vtgarment.model.view.outstading.OutStadingTableView;
import com.vtgarment.utils.Utils;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.hibernate.type.StringType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pakorn on 03/04/2016.
 */
@Repository
public class OutStadingDAO extends GenericDAO<String, Integer>{
    private final int twoDecimal = 2;
    @Value("#{config['style.red']}") private String red;
    @Value("#{config['style.green']}") private String green;
    @Value("#{config['arrow.up']}") private String up;
    @Value("#{config['arrow.down']}") private String down;

    public List<OutStadingTableView> getOutStading(int factoryId, int bildingFloorId, String lineId){
        List<OutStadingTableView> outStadingTableViewList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

//        sql.append(" SELECT today.code||today.name AS CODE_NAME, yesterday.rework_qty_actual AS YESTERDAY, today.rework_qty_actual AS TODAY")
//                .append(" FROM (SELECT line.id AS LINE_ID, line.code, line.name, rework_qty_actual, factory_id, building_floor.id AS FLOOR_ID FROM line")
//                .append(" LEFT JOIN production ON line.id = production.line_id")
//                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
//                .append(" WHERE date_part('year', plan_start) = date_part('year', current_timestamp)")
//                .append(" AND date_part('month', plan_start) = date_part('month', current_timestamp)")
//                .append(" AND date_part('day', plan_start) = date_part('day', current_timestamp)) AS today")
//                .append(" LEFT JOIN (SELECT line_id,rework_qty_actual FROM production")
//                .append(" WHERE (date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start) =")
//                .append(" (SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
//                .append(" FROM production WHERE id = (SELECT max(id) FROM production)))")
//                .append(" AND ((SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
//                .append(" FROM production WHERE id = (SELECT max(id) FROM production)) < date_part('year', current_timestamp) || '-' || date_part('month', current_timestamp) ||")
//                .append(" '-' ||date_part('day', current_timestamp))) AS Yesterday ON today.LINE_ID = yesterday.line_id WHERE 1 = 1");

        sql.append(" SELECT outstading_today.code||outstading_today.name AS CODE_NAME, outstading_yesterday.rework_qty_actual AS YESTERDAY, outstading_today.rework_qty_actual AS TODAY, outstading_today.rework_qty_target AS TARGET")
                .append(" FROM line")
                .append(" INNER JOIN (")
                .append(" SELECT line.id, code, name, rework_qty_actual, rework_qty_target")
                .append(" FROM line")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" WHERE date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" = date_part('year', current_timestamp) || '-' || date_part('month', current_timestamp) || '-' || date_part('day', current_timestamp)")
                .append(" ) AS outstading_today ON line.id = outstading_today.id")
                .append(" INNER JOIN (")
                .append(" SELECT production.line_id, rework_qty_actual")
                .append(" FROM production")
                .append(" LEFT JOIN workday ON production.line_id = workday.line_id")
                .append(" WHERE date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" = date_part('year', yesterday) || '-' || date_part('month', yesterday) || '-' || date_part('day', yesterday)")
                .append(" ) AS outstading_yesterday ON line.id = outstading_yesterday.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
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


        sql.append(" GROUP BY outstading_today.code, outstading_today.name, outstading_yesterday.rework_qty_actual, outstading_today.rework_qty_actual, outstading_today.rework_qty_target");
        sql.append(" ORDER BY outstading_today.code");

        log.debug("getOutStading : {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("CODE_NAME", StringType.INSTANCE)
                    .addScalar("YESTERDAY", IntegerType.INSTANCE)
                    .addScalar("TODAY", IntegerType.INSTANCE)
                    .addScalar("TARGET", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                OutStadingTableView outStadingTableView = new OutStadingTableView();

                outStadingTableView.setLineCode(Utils.parseString(entity[0]));
                outStadingTableView.setYesterDay(Utils.parseInt(entity[1]));
                outStadingTableView.setToDay(Utils.parseInt(entity[2]));

                outStadingTableView.setTarget(Utils.parseInt(entity[3]));

                if (Utils.compareInt(outStadingTableView.getToDay(), outStadingTableView.getTarget())){
                    outStadingTableView.setStyleToDay(green);
                } else {
                    outStadingTableView.setStyleToDay(red);
                }

                if (Utils.compareInt(outStadingTableView.getYesterDay(), outStadingTableView.getTarget())){
                    outStadingTableView.setStyleYesterDay(green);
                } else {
                    outStadingTableView.setStyleYesterDay(red);
                }

                if (Utils.compareInt(outStadingTableView.getToDay(), outStadingTableView.getYesterDay())){
                    outStadingTableView.setTrend(outStadingTableView.getYesterDay() - outStadingTableView.getToDay());
                    outStadingTableView.setImage(up);
                } else {
                    outStadingTableView.setTrend(outStadingTableView.getToDay() - outStadingTableView.getYesterDay());
                    outStadingTableView.setImage(down);
                }

                outStadingTableViewList.add(outStadingTableView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return outStadingTableViewList;
    }
}
