package com.vtgarment.model.dao;

import com.vtgarment.model.db.UserModel;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends GenericDAO<UserModel, Integer> {

    public UserModel findByUserNameAndPassword(String userName, String password) throws Exception {
        log.debug("{} : {}", userName, password);
        return (UserModel) getCriteria().add(Restrictions.and(
                        Restrictions.eq("username", userName),
                        Restrictions.eq("password", password))
        ).uniqueResult();
    }
}
