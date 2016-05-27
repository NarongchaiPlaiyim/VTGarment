package com.vtgarment.beans;

import com.vtgarment.model.view.System.ImportProductionPlanView;
import com.vtgarment.service.ImportProductionPlanService;
import com.vtgarment.utils.MessageDialog;
import com.vtgarment.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import java.util.ArrayList;

/**
 * Created by pakor on 24/05/2016.
 */
@Getter
@Setter
@ViewScoped
@ManagedBean(name = "importProductionPlanBean")
public class ImportProductionPlanBean extends Bean{
    @ManagedProperty("#{importProductionPlanService}") private ImportProductionPlanService importProductionPlanService;

    private ArrayList<ImportProductionPlanView> list;
    private ArrayList<ImportProductionPlanView> filterlist;
    private boolean complete;

    @PostConstruct
    public void onCreation(){
        init();
    }

    private void init(){
        onLoad();
    }

    private void onLoad(){
        try {
            list = importProductionPlanService.findAll();
        } catch (Exception e) {
            log.debug("Exception onLoad error : {}", e);
        }
    }

    public void onSubmitImportCSV(FileUploadEvent event){
        log.debug("-- onSubmitImportCSV()");
        UploadedFile file = null;
        String fileName = null;
        try {
            file = event.getFile();
            fileName = file.getFileName();
            if(!Utils.isNull(fileName) && !Utils.isZero(fileName.length())){
                list = importProductionPlanService.importProcess(file.getInputstream());
            }
            log.debug("ImportProductionPlan size : {}", list.size());
        } catch (Exception e){
            log.error("Exception onSubmitImportCSV : {}", e);
        }
    }

    public void onDeplayProductionPlan(){

        try {
            importProductionPlanService.deploy(list);
            showDialog(MessageDialog.UPDATE.getMessageHeader(), "Success.", "msgBoxSystemMessageDlg");
        } catch (Exception e) {
            log.error("Exception onDeplayProductionPlan error : {}", e);
            showDialog(MessageDialog.WARNING.getMessageHeader(), e.getMessage(), "msgBoxSystemMessageDlg");
        }
    }
}
