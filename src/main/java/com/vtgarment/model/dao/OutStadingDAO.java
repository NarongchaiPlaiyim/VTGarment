package com.vtgarment.model.dao;

import com.vtgarment.model.view.outstading.OutStadingTableView;
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
public class OutStadingDAO extends GenericDAO<String, Integer>{
    private final int twoDecimal = 2;
    @Value("#{config['style.red']}") private String red;
    @Value("#{config['style.green']}") private String green;
    @Value("#{config['arrow.up']}") private String up;
    @Value("#{config['arrow.down']}") private String down;

    public List<OutStadingTableView> getOutStading(int factoryId, int bildingFloorId, int lineId, int leaderId){
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

        sql.append(" SELECT outstading_today.code||outstading_today.name AS CODE_NAME, outstading_yesterday.sew_otp_actual AS YESTERDAY, outstading_today.sew_otp_actual AS TODAY")
                .append(" FROM line")
                .append(" LEFT JOIN (")
                .append(" SELECT line.id, code, name, sew_otp_actual")
                .append(" FROM line")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" WHERE date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" = date_part('year', current_timestamp) || '-' || date_part('month', current_timestamp) || '-' || date_part('day', current_timestamp)")
                .append(" ) AS outstading_today ON line.id = outstading_today.id")
                .append(" LEFT JOIN (")
                .append(" SELECT production.line_id, sew_otp_actual")
                .append(" FROM production")
                .append(" LEFT JOIN workday ON production.line_id = workday.line_id")
                .append(" WHERE date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
                .append(" = date_part('year', yesterday) || '-' || date_part('month', yesterday) || '-' || date_part('day', yesterday)")
                .append(" ) AS outstading_yesterday ON line.id = outstading_yesterday.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" WHERE 1=1");

        if (!Utils.isZero(factoryId)){
            sql.append(" AND today.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(bildingFloorId)){
            sql.append(" AND today.FLOOR_ID =").append(bildingFloorId);
        }

        if (!Utils.isZero(lineId)){
            sql.append(" AND today.LINE_ID =").append(lineId);
        }

        if (!Utils.isZero(leaderId)){
            sql.append(" AND line.leader_id =").append(leaderId);
        }

        sql.append(" GROUP BY outstading_today.code, outstading_today.name, outstading_yesterday.sew_otp_actual, outstading_today.sew_otp_actual");

        log.debug("getOutStading : {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("CODE_NAME", StringType.INSTANCE)
                    .addScalar("YESTERDAY", BigDecimalType.INSTANCE)
                    .addScalar("TODAY", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                OutStadingTableView outStadingTableView = new OutStadingTableView();

                outStadingTableView.setLineCode(Utils.parseString(entity[0]));
                outStadingTableView.setYesterDay(Utils.parseBigDecimal(entity[1]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                outStadingTableView.setToDay(Utils.parseBigDecimal(entity[2]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                if (Utils.compareBigDecimal(outStadingTableView.getToDay(), outStadingTableView.getYesterDay())){
                    outStadingTableView.setTrend(outStadingTableView.getToDay().subtract(outStadingTableView.getYesterDay()).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                    outStadingTableView.setStyleToDay(red);
                    outStadingTableView.setStyleYesterDay(green);
                    outStadingTableView.setImage(down);
                } else {
                    outStadingTableView.setTrend(outStadingTableView.getYesterDay().subtract(outStadingTableView.getToDay()).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                    outStadingTableView.setStyleToDay(green);
                    outStadingTableView.setStyleYesterDay(red);
                    outStadingTableView.setImage(up);
                }

                outStadingTableViewList.add(outStadingTableView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return outStadingTableViewList;
    }
}
