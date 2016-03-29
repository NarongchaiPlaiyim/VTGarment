package com.vtgarment.model.dao;

import com.vtgarment.model.db.LineModel;
import com.vtgarment.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
}
