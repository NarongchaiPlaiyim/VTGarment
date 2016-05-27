package com.vtgarment.model.dao;

import com.vtgarment.model.db.LineModel;
import com.vtgarment.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.TimestampType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class LineDAO extends GenericDAO<LineModel, Integer> {

    public List<LineModel> findByBuildingFloorId(int buildingFloorId){
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("buildingFloorModel.id", buildingFloorId));

            return Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByFactoryId : {}", e);
            return new ArrayList<>();
        }
    }

    public String findLastUpdate(int factoryId, int buildingFloor, String lineId){

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT last_update AS LAST_UPDATE")
                .append(" FROM line")
                .append(" LEFT JOIN production ON line.id = production.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" LEFT JOIN user_line ON line.id = user_line.line_id")
                .append(" WHERE date_part('year', actual_start)||'-'||date_part('month', actual_start)||'-'||date_part('day', actual_start) =")
                .append(" date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)");

        if (!Utils.isZero(factoryId)){
            sql.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(buildingFloor)){
            sql.append(" AND building_floor.ID =").append(buildingFloor);
        }

        if (!Utils.isNull(lineId) && !Utils.isEmpty(lineId) && !"0".equals(lineId)){
            sql.append(" AND production.line_id in (").append(lineId).append(")");
        }

        sql.append(" ORDER BY last_update DESC LIMIT 1");

        log.debug("findOTPViewBySection : {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("LAST_UPDATE", TimestampType.INSTANCE);

            Date date = (Date) query.uniqueResult();

            return Utils.convertToStringDDMMYYYYHHmmss(date);

        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
            return "";
        }
    }

    public String findLastUpdateBreakdown(int factoryId, int buildingFloor, String lineId){

        StringBuilder sql = new StringBuilder();

        sql.append(" SELECT last_update AS LAST_UPDATE")
                .append(" FROM line")
                .append(" LEFT JOIN downtime ON line.id = downtime.line_id")
                .append(" LEFT JOIN building_floor ON line.building_floor_id = building_floor.id")
                .append(" LEFT JOIN user_line ON line.id = user_line.line_id")
                .append(" WHERE date_part('year', actual_start)||'-'||date_part('month', actual_start)||'-'||date_part('day', actual_start) =")
                .append(" date_part('year', current_timestamp)||'-'||date_part('month', current_timestamp)||'-'||date_part('day', current_timestamp)");

        if (!Utils.isZero(factoryId)){
            sql.append(" AND building_floor.factory_id =").append(factoryId);
        }

        if (!Utils.isZero(buildingFloor)){
            sql.append(" AND building_floor.ID =").append(buildingFloor);
        }

        if (!Utils.isNull(lineId) && !Utils.isEmpty(lineId) && !"0".equals(lineId)){
            sql.append(" AND production.line_id in (").append(lineId).append(")");
        }

        sql.append(" ORDER BY last_update DESC LIMIT 1");

        log.debug("findOTPViewBySection : {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("LAST_UPDATE", TimestampType.INSTANCE);

            Date date = (Date) query.uniqueResult();

            return Utils.convertToStringDDMMYYYYHHmmss(date);

        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
            return "";
        }
    }
}
