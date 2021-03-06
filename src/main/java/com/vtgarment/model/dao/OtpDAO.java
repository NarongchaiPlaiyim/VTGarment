package com.vtgarment.model.dao;

import com.vtgarment.model.view.otp.OtpTableView;
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
 * Created by pakorn on 01/04/2016.
 */
@Repository
public class OtpDAO extends GenericDAO<String, Integer> {

    private final int twoDecimal = 2;
    @Value("#{config['style.red']}") private String red;
    @Value("#{config['style.green']}") private String green;
    @Value("#{config['arrow.up']}") private String up;
    @Value("#{config['arrow.down']}") private String down;

    public List<OtpTableView> getOtp(int factoryId, int bildingFloorId, String lineId){
        List<OtpTableView> otpTableViewList = new ArrayList<>();
        StringBuilder sql = new StringBuilder();

//        sql.append(" SELECT today.code||today.name AS CODE_NAME, yesterday.sew_otp_actual AS YESTERDAY, today.sew_otp_actual AS TODAY")
//                .append(" FROM (SELECT line.id AS LINE_ID, line.code, line.name, sew_otp_actual, factory_id, building_floor.id AS FLOOR_ID FROM line")
//                .append(" LEFT JOIN production ON line.id = production.line_id")
//                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
//                .append(" WHERE date_part('year', plan_start) = date_part('year', current_timestamp)")
//                .append(" AND date_part('month', plan_start) = date_part('month', current_timestamp)")
//                .append(" AND date_part('day', plan_start) = date_part('day', current_timestamp)) AS today")
//                .append(" LEFT JOIN (SELECT line_id,sew_otp_actual FROM production")
//                .append(" WHERE (date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start) =")
//                .append(" (SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
//                .append(" FROM production WHERE id = (SELECT max(id) FROM production)))")
//                .append(" AND ((SELECT date_part('year', plan_start) || '-' || date_part('month', plan_start) || '-' || date_part('day', plan_start)")
//                .append(" FROM production WHERE id = (SELECT max(id) FROM production)) < date_part('year', current_timestamp) || '-' || date_part('month', current_timestamp) ||")
//                .append(" '-' ||date_part('day', current_timestamp))) AS Yesterday ON today.LINE_ID = yesterday.line_id WHERE 1 = 1");

        sql.append(" SELECT otp_today.code||otp_today.name AS CODE_NAME, otp_yesterday.sew_otp_actual AS YESTERDAY, otp_today.sew_otp_actual AS TODAY, otp_today.sew_otp_target AS TARGET")
                .append(" FROM line")
                .append(" INNER JOIN (")
                .append(" SELECT line.id, code, name, sew_otp_actual, sew_otp_target")
                .append(" FROM line")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" WHERE date_part('year', actual_start) || '-' || date_part('month', actual_start) || '-' || date_part('day', actual_start)")
                .append(" = date_part('year', current_timestamp) || '-' || date_part('month', current_timestamp) || '-' || date_part('day', current_timestamp)")
                .append(" ) AS otp_today ON line.id = otp_today.id")
                .append(" INNER JOIN (")
                .append(" SELECT production.line_id, sew_otp_actual")
                .append(" FROM production")
                .append(" LEFT JOIN workday ON production.line_id = workday.line_id")
                .append(" WHERE date_part('year', actual_start) || '-' || date_part('month', actual_start) || '-' || date_part('day', actual_start)")
                .append(" = date_part('year', yesterday) || '-' || date_part('month', yesterday) || '-' || date_part('day', yesterday)")
                .append(" ) AS otp_yesterday ON line.id = otp_yesterday.line_id")
                .append(" LEFT JOIN user_line ON line.id = user_line.line_id")
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

        sql.append(" GROUP BY otp_today.code, otp_today.name, otp_yesterday.sew_otp_actual, otp_today.sew_otp_actual, otp_today.sew_otp_target");
        sql.append(" ORDER BY otp_today.code");

        log.debug("getOtp : {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("CODE_NAME", StringType.INSTANCE)
                    .addScalar("YESTERDAY", BigDecimalType.INSTANCE)
                    .addScalar("TODAY", BigDecimalType.INSTANCE)
                    .addScalar("TARGET", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                OtpTableView otpTableView = new OtpTableView();

                otpTableView.setLineCode(Utils.parseString(entity[0]));
                otpTableView.setYesterDay(Utils.parseBigDecimal(entity[1]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                otpTableView.setToDay(Utils.parseBigDecimal(entity[2]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                otpTableView.setTarget(Utils.parseBigDecimal(entity[3]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                if (Utils.compareMoreBigDecimal(otpTableView.getToDay(), otpTableView.getTarget())){
                    otpTableView.setStyleToDay(green);
                } else {
                    otpTableView.setStyleToDay(red);
                }

                if (Utils.compareMoreBigDecimal(otpTableView.getYesterDay(), otpTableView.getTarget())){
                    otpTableView.setStyleYesterDay(green);
                } else {
                    otpTableView.setStyleYesterDay(red);
                }

                if (Utils.compareMoreBigDecimal(otpTableView.getToDay(), otpTableView.getYesterDay())){
                    otpTableView.setTrend(otpTableView.getToDay().subtract(otpTableView.getYesterDay()).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                    otpTableView.setImage(up);
                } else {
                    otpTableView.setTrend(otpTableView.getYesterDay().subtract(otpTableView.getToDay()).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                    otpTableView.setImage(down);
                }

                otpTableViewList.add(otpTableView);
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return otpTableViewList;
    }

}
