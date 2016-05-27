package com.vtgarment.model.dao;

import com.vtgarment.model.db.ProductionPlanModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

/**
 * Created by pakor on 27/05/2016.
 */
@Repository
public class ProductionPlanDAO extends GenericDAO<ProductionPlanModel, Integer> {

    public int getMaxId(){

        int maxId = 0;

        try {
            Criteria criteria = getSession()
                    .createCriteria(ProductionPlanModel.class)
                    .setProjection(Projections.max("id"));
            maxId = (Integer)criteria.uniqueResult();
        } catch (Exception e) {
            log.debug("Exception getMaxId error : {}", e);
        }

        return maxId;
    }
}
