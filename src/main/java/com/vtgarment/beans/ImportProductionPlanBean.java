package com.vtgarment.beans;

import com.vtgarment.model.view.System.ImportProductionPlanView;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
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

    private ArrayList<ImportProductionPlanView> list;
    private ArrayList<ImportProductionPlanView> filterlist;

    @PostConstruct
    public void onCreation(){
        init();
    }

    private void init(){
        list = new ArrayList<>();
        list.add(new ImportProductionPlanView("aaa", "bbb", "ccc", "DDD", "EEE"));
        list.add(new ImportProductionPlanView("aaa", "bbb", "ccc", "DDD", "EEE"));
        list.add(new ImportProductionPlanView("aaa", "bbb", "ccc", "DDD", "EEE"));
        list.add(new ImportProductionPlanView("aaa", "bbb", "ccc", "DDD", "EEE"));
        list.add(new ImportProductionPlanView("aaa", "bbb", "ccc", "DDD", "EEE"));
        list.add(new ImportProductionPlanView("aaa", "bbb", "ccc", "DDD", "EEE"));
        list.add(new ImportProductionPlanView("aaa", "bbb", "ccc", "DDD", "EEE"));

    }
}
