package com.vtgarment.model.dao;

import com.vtgarment.model.db.ProductionModel;
import com.vtgarment.model.view.breakdown.BreakDownCompareView;
import com.vtgarment.utils.Utils;
import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductionDAO extends GenericDAO<ProductionModel, Integer> {

    public BreakDownCompareView getCompateBreakdown(){
        StringBuilder sql = new StringBuilder();
        BreakDownCompareView breakDownTableView = new BreakDownCompareView();

        sql.append(" select downtime_man AS MAN, downtime_mach AS MACH, downtime_method AS METHOD, downtime_material AS MATERIAL from production limit 1");

                log.debug(" --getBreakDown {}", sql.toString());

        try {
            SQLQuery query = getSession().createSQLQuery(sql.toString())
                    .addScalar("MAN", IntegerType.INSTANCE)
                    .addScalar("MACH", IntegerType.INSTANCE)
                    .addScalar("METHOD", IntegerType.INSTANCE)
                    .addScalar("MATERIAL", IntegerType.INSTANCE);
            List<Object[]> objects = query.list();

            for (Object[] entity : objects) {
                breakDownTableView.setMan(Utils.parseInt(entity[0]));
                breakDownTableView.setMach(Utils.parseInt(entity[1]));
                breakDownTableView.setMethod(Utils.parseInt(entity[2]));
                breakDownTableView.setMaterial(Utils.parseInt(entity[3]));
            }
        } catch (Exception e) {
            log.debug("Exception SQL : {}", e);
        }

        return breakDownTableView;
    }
}
