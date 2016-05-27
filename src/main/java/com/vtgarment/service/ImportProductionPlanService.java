package com.vtgarment.service;

import com.vtgarment.model.dao.ProductionPlanDAO;
import com.vtgarment.model.db.ProductionPlanModel;
import com.vtgarment.model.view.ImportProductionPlanCSVView;
import com.vtgarment.model.view.System.ImportProductionPlanView;
import com.vtgarment.transform.ImportProductionPlanTransform;
import com.vtgarment.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pakor on 27/05/2016.
 */
@Component
@Transactional
public class ImportProductionPlanService extends Service{
    @Resource private CSVService csvService;
    @Resource private ProductionPlanDAO productionPlanDAO;
    @Resource private ImportProductionPlanTransform importProductionPlanTransform;

    public ArrayList<ImportProductionPlanView> findAll() throws Exception{
        return importProductionPlanTransform.transformModelListToViewList(productionPlanDAO.findAll());
    }

    public ArrayList<ImportProductionPlanView> importProcess(final InputStream inputStream) throws Exception{
        log.debug("-- importProcess()");
        List<ImportProductionPlanCSVView> importProductionPlanCSVViews = Utils.safetyList(csvService.CSVImport(inputStream));
        return importProductionPlanTransform.transformViewListToViewList(importProductionPlanCSVViews);
    }

    public void deploy(ArrayList<ImportProductionPlanView> viewList) throws Exception{
        log.debug("--deploy() : {}",viewList);

        ArrayList<ProductionPlanModel> modelList = importProductionPlanTransform.transformViewListToModelList(viewList, productionPlanDAO.getMaxId());

        productionPlanDAO.persist(modelList);
    }
}
