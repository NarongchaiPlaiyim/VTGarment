package com.vtgarment.model.dao;

import com.vtgarment.model.view.breakdown.BreakDownView;
import com.vtgarment.model.view.otp.OtpView;
import com.vtgarment.model.view.outstading.OutstadingView;
import com.vtgarment.model.view.rework.ReworkView;
import com.vtgarment.utils.Utils;
import org.hibernate.SQLQuery;
import org.hibernate.type.BigDecimalType;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by pakorn on 31/03/2016.
 */
@Repository
public class DashboardDAO extends GenericDAO<String, Integer>{

    private final String ALL = "All";
    private final int twoDecimal = 2;
    private StringBuilder select;
    private StringBuilder leftJoin;
    private final String CROSSJOIN = " CROSS JOIN";
    private StringBuilder where;

    public OtpView findOTPViewBySection(int factoryId, int buildingFloor, String lineId){
        OtpView otpView = new OtpView();

        StringBuilder sql = new StringBuilder();
        where = new StringBuilder();

        if (!Utils.isZero(factoryId)){
            where.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(buildingFloor)){
            where.append(" AND building_floor.ID =").append(buildingFloor);
        }

        if (!Utils.isNull(lineId) && !Utils.isEmpty(lineId) && !"0".equals(lineId)){
            where.append(" AND production.line_id in (").append(lineId).append(")");
        }

        sql.append(" SELECT MAX_OTP.CODE AS LINE_BEST_CODE, MAX_OTP.OTP AS BEST_OTP_ACTUAL, MAX_OTP.TARGET AS BEST_OTP_TARGET,")
                .append(" MIN_OTP.CODE AS LINE_WORST_CODE, MIN_OTP.OTP AS WORST_OTP_ACTUAL, MIN_OTP.TARGET AS WORST_OTP_TARGET,")
                .append(" AVG (production.sew_otp_actual) AS TREND_ACTUAL, AVG (production.sew_otp_target) AS TREND_TARGET")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID")
                .append(" CROSS JOIN ( SELECT production.sew_otp_actual AS OTP, line.code AS CODE, production.sew_otp_target AS TARGET")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line. ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor. ID")
                .append(" WHERE production.sew_otp_actual = ( SELECT MAX (production.sew_otp_actual)")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line. ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID")
                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(where.toString()).append(")").append(where.toString())
                .append(" GROUP BY line.code, production.sew_otp_actual, production.sew_otp_target) AS MAX_OTP")

                .append(" CROSS JOIN ( SELECT production.sew_otp_actual AS OTP, line.code AS CODE, production.sew_otp_target AS TARGET")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line. ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor. ID")
                .append(" WHERE production.sew_otp_actual = ( SELECT MIN (production.sew_otp_actual)")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line. ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID")
                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(where.toString()).append(")").append(where.toString())
                .append(" GROUP BY line.code, production.sew_otp_actual, production.sew_otp_target) AS MIN_OTP")

                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(where.toString())
                .append(" GROUP BY MAX_OTP.CODE, MAX_OTP.OTP, MAX_OTP.TARGET,")
                .append(" MIN_OTP.OTP, MIN_OTP.CODE, MIN_OTP.TARGET");


        log.debug("findOTPViewBySection : {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("LINE_BEST_CODE", StringType.INSTANCE)
                    .addScalar("BEST_OTP_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("BEST_OTP_TARGET", BigDecimalType.INSTANCE)
                    .addScalar("LINE_WORST_CODE", StringType.INSTANCE)
                    .addScalar("WORST_OTP_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("WORST_OTP_TARGET", BigDecimalType.INSTANCE)
                    .addScalar("TREND_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("TREND_TARGET", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                otpView.setBestLineCode(Utils.parseString(entity[0]));
                otpView.setBestActual(Utils.parseBigDecimal(entity[1]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                otpView.setBestTarget(Utils.parseBigDecimal(entity[2]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                otpView.setWorstLineCode(Utils.parseString(entity[3]));
                otpView.setWorstActual(Utils.parseBigDecimal(entity[4]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                otpView.setWorstTarget(Utils.parseBigDecimal(entity[5]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                otpView.setTrendActual(Utils.parseBigDecimal(entity[6]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                otpView.setTrendTarget(Utils.parseBigDecimal(entity[7]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return otpView;
    }

    public ReworkView findReworkViewBySection(int factoryId, int buildingFloor, String lineId){
        ReworkView reworkView = new ReworkView();

        StringBuilder sql = new StringBuilder();
        where = new StringBuilder();

        if (!Utils.isZero(factoryId)){
            where.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(buildingFloor)){
            where.append(" AND building_floor.ID =").append(buildingFloor);
        }

        if (!Utils.isNull(lineId) && !Utils.isEmpty(lineId) && !"0".equals(lineId)){
            where.append(" AND production.line_id in (").append(lineId).append(")");
        }

        sql.append("SELECT MAX_REWORK.CODE AS LINE_BEST_CODE, MAX_REWORK.REWORK AS BEST_REWORK_ACTUAL, MAX_REWORK.TARGET AS BEST_REWORK_TARGET,")
                .append(" MIN_REWORK.CODE AS LINE_WORST_CODE, MIN_REWORK.REWORK AS WORST_REWORK_ACTUAL, MIN_REWORK.TARGET AS WORST_REWORK_TARGET,")
                .append(" AVG (production.rework_actual) AS TREND_ACTUAL, AVG (production.rework_target) AS TREND_TARGET")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID")
                .append(" CROSS JOIN ( SELECT production.rework_actual AS REWORK, line.code AS CODE, production.rework_target AS TARGET")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID")
                .append(" WHERE production.rework_actual = (")
                .append(" SELECT MAX (production.rework_actual)")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID")
                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(where.toString()).append(")").append(where.toString())
                .append(" GROUP BY line.code, production.rework_actual, production.rework_target ) AS MAX_REWORK")

                .append(" CROSS JOIN ( SELECT production.rework_actual AS REWORK, line.code AS CODE, production.rework_target AS TARGET")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID")
                .append(" WHERE production.rework_actual = (")
                .append(" SELECT MIN (production.rework_actual)")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID")
                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(where.toString()).append(")").append(where.toString())
                .append(" GROUP BY line.code, production.rework_actual, production.rework_target ) AS MIN_REWORK")

                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(where.toString())
                .append(" GROUP BY MAX_REWORK.CODE, MAX_REWORK.REWORK, MAX_REWORK.TARGET,")
                .append(" MIN_REWORK.REWORK, MIN_REWORK.CODE, MIN_REWORK.TARGET");


        log.debug("findReworkViewBySection : {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("LINE_BEST_CODE", StringType.INSTANCE)
                    .addScalar("BEST_REWORK_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("BEST_REWORK_TARGET", BigDecimalType.INSTANCE)
                    .addScalar("LINE_WORST_CODE", StringType.INSTANCE)
                    .addScalar("WORST_REWORK_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("WORST_REWORK_TARGET", BigDecimalType.INSTANCE)
                    .addScalar("TREND_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("TREND_TARGET", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                reworkView.setBestLineCode(Utils.parseString(entity[0]));
                reworkView.setBestActual(Utils.parseBigDecimal(entity[1]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                reworkView.setBestTarget(Utils.parseBigDecimal(entity[2]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                reworkView.setWorstLineCode(Utils.parseString(entity[3]));
                reworkView.setWorstActual(Utils.parseBigDecimal(entity[4]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                reworkView.setWorstTarget(Utils.parseBigDecimal(entity[5]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                reworkView.setTrendActual(Utils.parseBigDecimal(entity[6]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                reworkView.setTrendTarget(Utils.parseBigDecimal(entity[7]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return reworkView;
    }

    public BreakDownView findBreakDownViewBySection(int factoryId, int buildingFloor, String lineId){
        BreakDownView breakDownView = new BreakDownView();

        StringBuilder sql = new StringBuilder();

        where = new StringBuilder();

        if (!Utils.isZero(factoryId)){
            where.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(buildingFloor)){
            where.append(" AND building_floor.ID =").append(buildingFloor);
        }

        if (!Utils.isNull(lineId) && !Utils.isEmpty(lineId) && !"0".equals(lineId)){
            where.append(" AND production.line_id in (").append(lineId).append(")");
        }

        sql.append(" SELECT avg(total.downtime) AS TREND, maximum.code AS BEST_CODE, maximum.downtime AS BEST, minimum.code AS WORST_CODE, minimum.downtime AS WORST")
                .append(" FROM (SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" WHEN  0  THEN 1 ELSE")
                .append(" (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" END AS downtime, line.code, production.downtime_man, production.downtime_mach, production.downtime_method, production.downtime_material")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" WHERE date_part('year', actual_start)||'-'||date_part('month', actual_start)||'-'||date_part('day', actual_start) =")
                .append(" date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)")
                .append(where.toString())
                .append(" GROUP BY line.id, production.last_update, production.actual_start, line.code, production.downtime_man, production.downtime_mach, production.downtime_method, production.downtime_material")
                .append(" ) AS total")
                .append(" CROSS JOIN (SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" WHEN  0  THEN 1 ELSE")
                .append(" (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" END AS downtime, line.code")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" WHERE date_part('year', actual_start)||'-'||date_part('month', actual_start)||'-'||date_part('day', actual_start) =")
                .append(" date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)")
                .append(where.toString())
                .append(" GROUP BY line.id, production.last_update, production.actual_start, line.code")
                .append(" ORDER BY downtime DESC")
                .append(" LIMIT 1 ) AS maximum")
                .append(" CROSS JOIN (SELECT (sum(total_downtime) * 100) /")
                .append(" CASE (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" WHEN  0  THEN 1 ELSE")
                .append(" (date_part('hour', production.last_update - production.actual_start) * 3600) +")
                .append(" (date_part('min', production.last_update - production.actual_start) * 60) +")
                .append(" date_part('sec', production.last_update - production.actual_start)")
                .append(" END AS downtime, line.code")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" WHERE date_part('year', actual_start)||'-'||date_part('month', actual_start)||'-'||date_part('day', actual_start) =")
                .append(" date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)")
                .append(where.toString())
                .append(" GROUP BY line.id, production.last_update, production.actual_start, line.code")
                .append(" ORDER BY downtime")
                .append(" LIMIT 1 ) AS minimum")
                .append(" GROUP BY maximum.code, maximum.downtime, minimum.code, minimum.downtime");


        log.debug("findBreakDownViewBySection : {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("TREND", BigDecimalType.INSTANCE)
                    .addScalar("BEST_CODE", StringType.INSTANCE)
                    .addScalar("BEST", BigDecimalType.INSTANCE)
                    .addScalar("WORST_CODE", StringType.INSTANCE)
                    .addScalar("WORST", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                breakDownView.setTrendActual(Utils.parseBigDecimal(entity[0]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownView.setBestLineCode(Utils.parseString(entity[1]));
                breakDownView.setBestActual(Utils.parseBigDecimal(entity[2]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownView.setWorstLineCode(Utils.parseString(entity[3]));
                breakDownView.setWorstActual(Utils.parseBigDecimal(entity[4]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return breakDownView;
    }

    public OutstadingView findOutstadingViewBySection(int factoryId, int buildingFloor, String lineId){
        OutstadingView outstadingView = new OutstadingView();

        StringBuilder sql = new StringBuilder();
        where = new StringBuilder();

        if (!Utils.isZero(factoryId)){
            where.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(buildingFloor)){
            where.append(" AND building_floor.ID =").append(buildingFloor);
        }

        if (!Utils.isNull(lineId) && !Utils.isEmpty(lineId) && !"0".equals(lineId)){
            where.append(" AND production.line_id in (").append(lineId).append(")");
        }

        sql.append("SELECT MAX_OUTSTADING.CODE AS LINE_BEST_CODE, MAX_OUTSTADING.OUTSTADING AS BEST_OUTSTADING_ACTUAL, MAX_OUTSTADING.TARGET AS BEST_OUTSTADING_TARGET,")
                .append(" MIN_OUTSTADING.CODE AS LINE_WORST_CODE, MIN_OUTSTADING.OUTSTADING AS WORST_OUTSTADING_ACTUAL, MIN_OUTSTADING.TARGET AS WORST_OUTSTADING_TARGET,")
                .append(" AVG (production.rework_qty_actual) AS TREND_ACTUAL, AVG (production.rework_qty_target) AS TREND_TARGET")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID")
                .append(" CROSS JOIN ( SELECT production.rework_qty_actual AS OUTSTADING, line.code AS CODE, production.rework_qty_target AS TARGET")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID")
                .append(" WHERE production.rework_qty_actual = (SELECT MAX (production.rework_qty_actual)")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID")
                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(where.toString()).append(")").append(where.toString())
                .append(" GROUP BY line.code, production.rework_qty_actual, production.rework_qty_target) AS MAX_OUTSTADING")

                .append(" CROSS JOIN ( SELECT production.rework_qty_actual AS OUTSTADING, line.code AS CODE, production.rework_qty_target AS TARGET")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID")
                .append(" WHERE production.rework_qty_actual = (SELECT MIN (production.rework_qty_actual)")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.ID")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.ID")
                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(where.toString()).append(")").append(where.toString())
                .append(" GROUP BY line.code, production.rework_qty_actual, production.rework_qty_target) AS MIN_OUTSTADING")

                .append(" WHERE date_part('year', plan_start) = date_part('year', CURRENT_TIMESTAMP)")
                .append(" AND date_part('month', plan_start) = date_part('month', CURRENT_TIMESTAMP)")
                .append(" AND date_part('day', plan_start) = date_part('day', CURRENT_TIMESTAMP)")
                .append(where.toString())
                .append(" GROUP BY MAX_OUTSTADING.CODE, MAX_OUTSTADING.OUTSTADING, MAX_OUTSTADING.TARGET,")
                .append(" MIN_OUTSTADING.OUTSTADING, MIN_OUTSTADING.CODE, MIN_OUTSTADING.TARGET");

        log.debug("findOUTSTADINGViewBySection : {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("LINE_BEST_CODE", StringType.INSTANCE)
                    .addScalar("BEST_OUTSTADING_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("BEST_OUTSTADING_TARGET", BigDecimalType.INSTANCE)
                    .addScalar("LINE_WORST_CODE", StringType.INSTANCE)
                    .addScalar("WORST_OUTSTADING_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("WORST_OUTSTADING_TARGET", BigDecimalType.INSTANCE)
                    .addScalar("TREND_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("TREND_TARGET", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                outstadingView.setBestLineCode(Utils.parseString(entity[0]));
                outstadingView.setBestActual(Utils.parseInt(entity[1]));
                outstadingView.setBestTarget(Utils.parseInt(entity[2]));

                outstadingView.setWorstLineCode(Utils.parseString(entity[3]));
                outstadingView.setWorstActual(Utils.parseInt(entity[4]));
                outstadingView.setWorstTarget(Utils.parseInt(entity[5]));

                outstadingView.setTrendActual(Utils.parseInt(entity[6]));
                outstadingView.setTrendTarget(Utils.parseInt(entity[7]));
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return outstadingView;
    }
}
