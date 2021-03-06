package com.vtgarment.transform;

import com.vtgarment.model.db.ProductionPlanModel;
import com.vtgarment.model.erp.ProductionPlanModelERP;
import com.vtgarment.model.view.ImportProductionPlanCSVView;
import com.vtgarment.model.view.System.ImportProductionPlanView;
import com.vtgarment.utils.Utils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pakor on 27/05/2016.
 */
@Component
@Transactional
public class ImportProductionPlanTransform extends Transform{

    public ArrayList<ImportProductionPlanView> transformModelListToViewList(List<ProductionPlanModel> planModelList){
        log.debug("-- transformModelListToViewList : {}", planModelList.size());
        ArrayList<ImportProductionPlanView> importProductionPlanViewList = new ArrayList<>();

        for (ProductionPlanModel view : planModelList){
            importProductionPlanViewList.add(transformModelToView(view));
        }

        return importProductionPlanViewList;
    }

    public ImportProductionPlanView transformModelToView(ProductionPlanModel planModel){
//        log.debug("-- transformModel()");
        ImportProductionPlanView importProductionPlanView = new ImportProductionPlanView();

        importProductionPlanView.setLine(planModel.getLine());
        importProductionPlanView.setCo(planModel.getCo());
        importProductionPlanView.setQty(String.valueOf(planModel.getQty()));
        importProductionPlanView.setShipment(Utils.convertCurrentDateToStringDDMMYYYY(planModel.getShipment()));
        importProductionPlanView.setStyle(planModel.getStyle());

        return importProductionPlanView;
    }

    public ArrayList<ImportProductionPlanView> transformViewListToViewList(List<ImportProductionPlanCSVView> csvViewsList){
        log.debug("-- transformViewListToViewList : {}", csvViewsList.size());
        ArrayList<ImportProductionPlanView> importProductionPlanViewList = new ArrayList<>();

        for (ImportProductionPlanCSVView view : csvViewsList){
            importProductionPlanViewList.add(transformViewToView(view));
        }

        return importProductionPlanViewList;
    }

    public ImportProductionPlanView transformViewToView(ImportProductionPlanCSVView csvView){
//        log.debug("-- transformModel()");
        ImportProductionPlanView importProductionPlanView = new ImportProductionPlanView();

        importProductionPlanView.setLine(csvView.getLine());
        importProductionPlanView.setCo(csvView.getCO());
        importProductionPlanView.setQty(csvView.getQty());
        importProductionPlanView.setShipment(csvView.getShipment());
        importProductionPlanView.setStyle(csvView.getStyle());

        return importProductionPlanView;
    }

    public ArrayList<ProductionPlanModel> transformViewListToModelList(ArrayList<ImportProductionPlanView> viewList){
        log.debug("--transformViewListToModelList()");
        ArrayList<ProductionPlanModel> modelList = new ArrayList<>();

        for (ImportProductionPlanView view : viewList){
            modelList.add(transformViewToModel(view));
        }

        return modelList;
    }

    public ProductionPlanModel transformViewToModel(ImportProductionPlanView view){
        ProductionPlanModel model = new ProductionPlanModel();

//        model.setId(id);
        model.setLine(view.getLine());
        model.setStyle(view.getStyle());
        model.setCo(view.getCo());
        model.setQty(Integer.parseInt(view.getQty()));

        try {
            model.setShipment(Utils.convertStringToDate(view.getShipment()));
        } catch (Exception e) {
            log.debug("Exception shipment is null : {}", e);
        }

        log.debug(" model : {}", model.toString());

        return model;
    }

    public ArrayList<ProductionPlanModelERP> transformViewListToModelListERP(ArrayList<ImportProductionPlanView> viewList){
        log.debug("--transformViewListToModelList()");
        ArrayList<ProductionPlanModelERP> modelListERP = new ArrayList<>();

        for (ImportProductionPlanView view : viewList){
            modelListERP.add(transformViewToModelERP(view));
        }

        return modelListERP;
    }

    public ProductionPlanModelERP transformViewToModelERP(ImportProductionPlanView view){
        ProductionPlanModelERP modelERP = new ProductionPlanModelERP();

//        model.setId(id);
        modelERP.setLine(view.getLine());
        modelERP.setStyle(view.getStyle());
        modelERP.setCo(view.getCo());
        modelERP.setQty(Integer.parseInt(view.getQty()));

        try {
            modelERP.setShipment(Utils.convertStringToDate(view.getShipment()));
        } catch (Exception e) {
            log.debug("Exception shipment is null : {}", e);
        }

        log.debug(" model : {}", modelERP.toString());

        return modelERP;
    }
}
