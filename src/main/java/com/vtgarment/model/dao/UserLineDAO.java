package com.vtgarment.model.dao;

import com.vtgarment.model.db.UserLineModel;
import com.vtgarment.utils.Utils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by pakorn on 09/04/2016.
 */
@Repository
public class UserLineDAO extends GenericDAO<UserLineModel, Integer>{

    public List<UserLineModel> findLineIdByUserId(int userId) throws Exception {
        Criteria criteria = getCriteria().add(Restrictions.eq("userId", userId));
        log.debug("Size User Line : {}", criteria.list().size());
        return Utils.safetyList(criteria.list());
    }
}
