package com.vtgarment.model.dao;

import com.vtgarment.model.db.BuildingFloorModel;
import com.vtgarment.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BuildingFloorDAO extends GenericDAO<BuildingFloorModel, Integer> {

    public List<BuildingFloorModel> findByFactoryId(int factoryId){
        try {
            Criteria criteria = getCriteria();
            criteria.add(Restrictions.eq("factoryByFactoryId.id", factoryId));

            return Utils.safetyList(criteria.list());
        } catch (Exception e) {
            log.debug("Exception error findByFactoryId : {}", e);
            return new ArrayList<>();
        }
    }
}
