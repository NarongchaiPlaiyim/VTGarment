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


    public OtpView findOTPView(int lineId){
        OtpView otpView = new OtpView();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" AVG(production.sew_otp_actual) AS TREND_ACTUAL,");
        sqlBuilder.append(" AVG(production.sew_otp_target) AS TREND_TARGET");
        sqlBuilder.append(" FROM line");
        sqlBuilder.append(" LEFT JOIN production ON line.id = production.line_id");
        sqlBuilder.append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id");
        sqlBuilder.append(" WHERE date_part('year', production.plan_start) = date_part('year', current_timestamp)");
        sqlBuilder.append(" AND date_part('month', production.plan_start) = date_part('month', current_timestamp)");
        sqlBuilder.append(" AND date_part('day', production.plan_start) = date_part('day', current_timestamp)");

        if (!Utils.isZero(lineId)){
            sqlBuilder.append(" AND production.line_id =").append(lineId);
        }

        log.debug("findOTPView : {}", sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("TREND_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("TREND_TARGET", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                otpView.setTrendActual(new BigDecimal(Utils.parseInt(entity[0])).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                otpView.setTrendTarget(new BigDecimal(Utils.parseInt(entity[1])).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return otpView;
    }

    public OtpView findOTPViewBySection(int factoryId, int buildingFloor, int lineId){
        OtpView otpView = new OtpView();

        StringBuilder sql = new StringBuilder();

        StringBuilder cross = new StringBuilder();

        select = new StringBuilder();
        leftJoin = new StringBuilder();
        where = new StringBuilder();

        select.append(" SELECT MAX_OTP.CODE AS LINE_BEST_CODE, MAX_OTP.OTP AS BEST_OTP_ACTUAL, MAX_OTP.TARGET AS BEST_OTP_TARGET,")
                .append(" MIN_OTP.CODE AS LINE_WORST_CODE, MIN_OTP.OTP AS WORST_OTP_ACTUAL, MIN_OTP.TARGET AS WORST_OTP_TARGET,")
                .append(" avg(production.sew_otp_actual) AS TREND_ACTUAL, avg(production.sew_otp_target) AS TREND_TARGET")
                .append(" FROM production");

        leftJoin.append(" LEFT JOIN line ON production.line_id = line.id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id");

        where.append(" WHERE date_part('year', plan_start) = date_part('year', current_timestamp)")
                .append(" AND date_part('month', plan_start) = date_part('month', current_timestamp)")
                .append(" AND date_part('day', plan_start) = date_part('day', current_timestamp)");

        cross.append(" (SELECT production.sew_otp_actual AS OTP, line.code AS CODE, production.sew_otp_target AS TARGET")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.id");

        if (!Utils.isZero(factoryId)){
            where.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(buildingFloor)){
            where.append(" AND building_floor.id =").append(buildingFloor);
        }

        if (!Utils.isZero(lineId)){
            where.append(" AND production.line_id =").append(lineId);
        }

        sql.append(select.toString()).append(leftJoin.toString())
                .append(CROSSJOIN).append(cross.toString())
                .append(" WHERE production.sew_otp_actual = (select MAX(production.sew_otp_actual)")
                .append(" FROM production")
                .append(leftJoin.toString()).append(where.toString())
                .append(" )GROUP BY line.code, production.sew_otp_actual, production.sew_otp_target) AS MAX_OTP")
                .append(CROSSJOIN).append(cross.toString())
                .append(" WHERE production.sew_otp_actual = (select MIN(production.sew_otp_actual)")
                .append(" FROM production")
                .append(leftJoin.toString()).append(where.toString())
                .append(" )GROUP BY line.code,production.sew_otp_actual, production.sew_otp_target) AS MIN_OTP")
                .append(where.toString())
                .append(" GROUP BY MAX_OTP.CODE, MAX_OTP.OTP, MAX_OTP.TARGET, MIN_OTP.OTP, MIN_OTP.CODE, MIN_OTP.TARGET");


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

    public ReworkView findReworkView(int lineId){
        ReworkView reworkView = new ReworkView();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" AVG(production.rework_actual) AS TREND_ACTUAL,");
        sqlBuilder.append(" AVG(production.rework_target) AS TREND_TARGET");
        sqlBuilder.append(" FROM line");
        sqlBuilder.append(" LEFT JOIN production ON line.id = production.line_id");
        sqlBuilder.append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id");
        sqlBuilder.append(" WHERE date_part('year', production.plan_start) = date_part('year', current_timestamp)");
        sqlBuilder.append(" AND date_part('month', production.plan_start) = date_part('month', current_timestamp)");
        sqlBuilder.append(" AND date_part('day', production.plan_start) = date_part('day', current_timestamp)");

        if (!Utils.isZero(lineId)){
            sqlBuilder.append(" AND production.line_id =").append(lineId);
        }

        log.debug("findOTPView : {}", sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("TREND_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("TREND_TARGET", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                reworkView.setTrendActual(new BigDecimal(Utils.parseInt(entity[0])).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                reworkView.setTrendTarget(new BigDecimal(Utils.parseInt(entity[1])).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return reworkView;
    }

    public ReworkView findReworkViewBySection(int factoryId, int buildingFloor, int lineId){
        ReworkView reworkView = new ReworkView();

        StringBuilder sql = new StringBuilder();

        StringBuilder cross = new StringBuilder();

        select = new StringBuilder();
        leftJoin = new StringBuilder();
        where = new StringBuilder();

        select.append(" SELECT MAX_REWORK.CODE AS LINE_BEST_CODE, MAX_REWORK.REWORK AS BEST_REWORK_ACTUAL, MAX_REWORK.TARGET AS BEST_REWORK_TARGET,")
                .append(" MIN_REWORK.CODE AS LINE_WORST_CODE, MIN_REWORK.REWORK AS WORST_REWORK_ACTUAL, MIN_REWORK.TARGET AS WORST_REWORK_TARGET,")
                .append(" avg(production.rework_actual) AS TREND_ACTUAL, avg(production.rework_target) AS TREND_TARGET")
                .append(" FROM production");

        leftJoin.append(" LEFT JOIN line ON production.line_id = line.id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id");

        where.append(" WHERE date_part('year', plan_start) = date_part('year', current_timestamp)")
                .append(" AND date_part('month', plan_start) = date_part('month', current_timestamp)")
                .append(" AND date_part('day', plan_start) = date_part('day', current_timestamp)");

        cross.append(" (SELECT production.rework_actual AS REWORK, line.code AS CODE, production.rework_target AS TARGET")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.id");

        if (!Utils.isZero(factoryId)){
            where.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(buildingFloor)){
            where.append(" AND building_floor.id =").append(buildingFloor);
        }

        if (!Utils.isZero(lineId)){
            where.append(" AND production.line_id =").append(lineId);
        }

        sql.append(select.toString()).append(leftJoin.toString())
                .append(CROSSJOIN).append(cross.toString())
                .append(" WHERE production.rework_actual = (select MAX(production.rework_actual)")
                .append(" FROM production")
                .append(leftJoin.toString()).append(where.toString())
                .append(" )GROUP BY line.code, production.rework_actual, production.rework_target) AS MAX_REWORK")
                .append(CROSSJOIN).append(cross.toString())
                .append(" WHERE production.rework_actual = (select MIN(production.rework_actual)")
                .append(" FROM production")
                .append(leftJoin.toString()).append(where.toString())
                .append(" )GROUP BY line.code,production.rework_actual, production.rework_target) AS MIN_REWORK")
                .append(where.toString())
                .append(" GROUP BY MAX_REWORK.CODE, MAX_REWORK.REWORK, MAX_REWORK.TARGET, MIN_REWORK.REWORK, MIN_REWORK.CODE, MIN_REWORK.TARGET");


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

    public BreakDownView findBreakDownView(int lineId){
        BreakDownView breakDownView = new BreakDownView();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" AVG(downtime.total_downtime) AS TREND_ACTUAL,");
        sqlBuilder.append(" AVG(production.downtime_targer) AS TREND_TARGET");
        sqlBuilder.append(" FROM downtime");
        sqlBuilder.append(" RIGHT JOIN production ON production.id = downtime.production_id");
        sqlBuilder.append(" LEFT JOIN line ON production.line_id = line.id");
        sqlBuilder.append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id ");
        sqlBuilder.append(" WHERE date_part('year', production.plan_start) = date_part('year', current_timestamp) ");
        sqlBuilder.append(" AND date_part('month', production.plan_start) = date_part('month', current_timestamp)");
        sqlBuilder.append(" AND date_part('day', production.plan_start) = date_part('day', current_timestamp)");
        if (!Utils.isZero(lineId)){
            sqlBuilder.append(" AND production.line_id =").append(lineId);
        }

        log.debug("findOTPView : {}", sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("TREND_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("TREND_TARGET", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                breakDownView.setTrendActual(new BigDecimal(Utils.parseInt(entity[0])).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownView.setTrendTarget(new BigDecimal(Utils.parseInt(entity[1])).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return breakDownView;
    }

    public BreakDownView findBreakDownViewBySection(int factoryId, int buildingFloor, int lineId){
        BreakDownView breakDownView = new BreakDownView();

        StringBuilder sql = new StringBuilder();

        StringBuilder cross = new StringBuilder();

        select = new StringBuilder();
        leftJoin = new StringBuilder();
        where = new StringBuilder();

        select.append(" SELECT MAX_BREAKDOWN.CODE AS LINE_BEST_CODE, MAX_BREAKDOWN.BREAKDOWN AS BEST_BREAKDOWN_ACTUAL, MAX_BREAKDOWN.TARGET AS BEST_BREAKDOWN_TARGET,")
                .append(" MIN_BREAKDOWN.CODE AS LINE_WORST_CODE, MIN_BREAKDOWN.BREAKDOWN AS WORST_BREAKDOWN_ACTUAL, MIN_BREAKDOWN.TARGET AS WORST_BREAKDOWN_TARGET,")
                .append(" avg(downtime.total_downtime) AS TREND_ACTUAL, avg(production.downtime_targer) AS TREND_TARGET")
                .append(" FROM downtime");

        leftJoin.append(" RIGHT JOIN production ON production.id = downtime.production_id")
                .append(" LEFT JOIN line ON production.line_id = line.id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id");

        where.append(" WHERE date_part('year', plan_start) = date_part('year', current_timestamp)")
                .append(" AND date_part('month', plan_start) = date_part('month', current_timestamp)")
                .append(" AND date_part('day', plan_start) = date_part('day', current_timestamp)");

        if (!Utils.isZero(factoryId)){
            where.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(buildingFloor)){
            where.append(" AND building_floor.id =").append(buildingFloor);
        }

        if (!Utils.isZero(lineId)){
            where.append(" AND production.line_id =").append(lineId);
        }

        cross.append(" (SELECT downtime.total_downtime AS BREAKDOWN, line.code AS CODE, production.downtime_targer AS TARGET")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.id")
                .append(" LEFT JOIN downtime ON downtime.production_id = production.id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id");

        sql.append(select.toString()).append(leftJoin.toString())
                .append(CROSSJOIN).append(cross.toString())
                .append(" WHERE downtime.total_downtime = (select MAX(downtime.total_downtime)")
                .append(" FROM downtime")
                .append(" RIGHT JOIN production ON production.id = downtime.production_id")
                .append(" LEFT JOIN line ON production.line_id = line.id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(where.toString())
                .append(" )GROUP BY line.code, downtime.total_downtime, production.downtime_targer) AS MAX_BREAKDOWN")
                .append(CROSSJOIN).append(cross.toString())
                .append(" WHERE downtime.total_downtime = (select MIN(downtime.total_downtime)")
                .append(" FROM downtime")
                .append(" RIGHT JOIN production ON production.id = downtime.production_id")
                .append(" LEFT JOIN line ON production.line_id = line.id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(where.toString())
                .append(" )GROUP BY line.code, downtime.total_downtime, production.downtime_targer) AS MIN_BREAKDOWN")
                .append(where.toString())
                .append(" GROUP BY MAX_BREAKDOWN.CODE, MAX_BREAKDOWN.BREAKDOWN, MAX_BREAKDOWN.TARGET, MIN_BREAKDOWN.BREAKDOWN, MIN_BREAKDOWN.CODE, MIN_BREAKDOWN.TARGET");


        log.debug("findBreakDownViewBySection : {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("LINE_BEST_CODE", StringType.INSTANCE)
                    .addScalar("BEST_BREAKDOWN_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("BEST_BREAKDOWN_TARGET", BigDecimalType.INSTANCE)
                    .addScalar("LINE_WORST_CODE", StringType.INSTANCE)
                    .addScalar("WORST_BREAKDOWN_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("WORST_BREAKDOWN_TARGET", BigDecimalType.INSTANCE)
                    .addScalar("TREND_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("TREND_TARGET", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                breakDownView.setBestLineCode(Utils.parseString(entity[0]));
                breakDownView.setBestActual(Utils.parseBigDecimal(entity[1]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownView.setBestTarget(Utils.parseBigDecimal(entity[2]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                breakDownView.setWorstLineCode(Utils.parseString(entity[3]));
                breakDownView.setWorstActual(Utils.parseBigDecimal(entity[4]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownView.setWorstTarget(Utils.parseBigDecimal(entity[5]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                breakDownView.setTrendActual(Utils.parseBigDecimal(entity[6]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                breakDownView.setTrendTarget(Utils.parseBigDecimal(entity[7]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return breakDownView;
    }

    public OutstadingView findOutstadingView(int lineId){
        OutstadingView outstadingView = new OutstadingView();
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(" SELECT ");
        sqlBuilder.append(" AVG(production.rework_qty_actual) AS TREND_ACTUAL,");
        sqlBuilder.append(" AVG(production.rework_qty_target) AS TREND_TARGET");
        sqlBuilder.append(" FROM line");
        sqlBuilder.append(" LEFT JOIN production ON line.id = production.line_id");
        sqlBuilder.append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id");
        sqlBuilder.append(" WHERE date_part('year', production.plan_start) = date_part('year', current_timestamp)");
        sqlBuilder.append(" AND date_part('month', production.plan_start) = date_part('month', current_timestamp)");
        sqlBuilder.append(" AND date_part('day', production.plan_start) = date_part('day', current_timestamp)");

        if (!Utils.isZero(lineId)){
            sqlBuilder.append(" AND production.line_id =").append(lineId);
        }

        log.debug("findOTPView : {}", sqlBuilder.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sqlBuilder.toString())
                    .addScalar("TREND_ACTUAL", BigDecimalType.INSTANCE)
                    .addScalar("TREND_TARGET", BigDecimalType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                outstadingView.setTrendActual(new BigDecimal(Utils.parseInt(entity[0])).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                outstadingView.setTrendTarget(new BigDecimal(Utils.parseInt(entity[1])).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return outstadingView;
    }

    public OutstadingView findOutstadingViewBySection(int factoryId, int buildingFloor, int lineId){
        OutstadingView outstadingView = new OutstadingView();

        StringBuilder sql = new StringBuilder();

        StringBuilder cross = new StringBuilder();

        select = new StringBuilder();
        leftJoin = new StringBuilder();
        where = new StringBuilder();

        select.append(" SELECT MAX_OUTSTADING.CODE AS LINE_BEST_CODE, MAX_OUTSTADING.OUTSTADING AS BEST_OUTSTADING_ACTUAL, MAX_OUTSTADING.TARGET AS BEST_OUTSTADING_TARGET,")
                .append(" MIN_OUTSTADING.CODE AS LINE_WORST_CODE, MIN_OUTSTADING.OUTSTADING AS WORST_OUTSTADING_ACTUAL, MIN_OUTSTADING.TARGET AS WORST_OUTSTADING_TARGET,")
                .append(" avg(production.rework_qty_actual) AS TREND_ACTUAL, avg(production.rework_qty_target) AS TREND_TARGET")
                .append(" FROM production");

        leftJoin.append(" LEFT JOIN line ON production.line_id = line.id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id");

        where.append(" WHERE date_part('year', plan_start) = date_part('year', current_timestamp)")
                .append(" AND date_part('month', plan_start) = date_part('month', current_timestamp)")
                .append(" AND date_part('day', plan_start) = date_part('day', current_timestamp)");

        cross.append(" (SELECT production.rework_qty_actual AS OUTSTADING, line.code AS CODE, production.rework_qty_target AS TARGET")
                .append(" FROM production")
                .append(" LEFT JOIN line ON production.line_id = line.id");

        if (!Utils.isZero(factoryId)){
            where.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(buildingFloor)){
            where.append(" AND building_floor.id =").append(buildingFloor);
        }

        if (!Utils.isZero(lineId)){
            where.append(" AND production.line_id =").append(lineId);
        }

        sql.append(select.toString()).append(leftJoin.toString())
                .append(CROSSJOIN).append(cross.toString())
                .append(" WHERE production.rework_qty_actual = (select MAX(production.rework_qty_actual)")
                .append(" FROM production")
                .append(leftJoin.toString()).append(where.toString())
                .append(" )GROUP BY line.code, production.rework_qty_actual, production.rework_qty_target) AS MAX_OUTSTADING")
                .append(CROSSJOIN).append(cross.toString())
                .append(" WHERE production.rework_qty_actual = (select MIN(production.rework_qty_actual)")
                .append(" FROM production")
                .append(leftJoin.toString()).append(where.toString())
                .append(" )GROUP BY line.code,production.rework_qty_actual, production.rework_qty_target) AS MIN_OUTSTADING")
                .append(where.toString())
                .append(" GROUP BY MAX_OUTSTADING.CODE, MAX_OUTSTADING.OUTSTADING, MAX_OUTSTADING.TARGET, MIN_OUTSTADING.OUTSTADING, MIN_OUTSTADING.CODE, MIN_OUTSTADING.TARGET");


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
                outstadingView.setBestActual(Utils.parseBigDecimal(entity[1]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                outstadingView.setBestTarget(Utils.parseBigDecimal(entity[2]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                outstadingView.setWorstLineCode(Utils.parseString(entity[3]));
                outstadingView.setWorstActual(Utils.parseBigDecimal(entity[4]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                outstadingView.setWorstTarget(Utils.parseBigDecimal(entity[5]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));

                outstadingView.setTrendActual(Utils.parseBigDecimal(entity[6]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
                outstadingView.setTrendTarget(Utils.parseBigDecimal(entity[7]).setScale(twoDecimal, BigDecimal.ROUND_HALF_EVEN));
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return outstadingView;
    }
}
