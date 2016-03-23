package com.vtgarment.beans;

import com.vtgarment.model.view.rework.ReworkTableView;
import com.vtgarment.utils.DateUtil;
import com.vtgarment.utils.Utils;
import lombok.Setter;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//@Getter
@Setter
@ApplicationScoped
@ManagedBean(name = "centralBean")
public class CentralBean extends Bean {
    private List<ReworkTableView> reworkTableViewList;
    private String previousDate;
    private String currentDate;

    public void CentralBean() {
        log.debug("called");
        previousDate = DateUtil.convertDateToString_DD_MM_YYYY(Utils.previousDate());
        currentDate = DateUtil.convertDateToString_DD_MM_YYYY(Utils.currentDate());

    }

    public int randInt(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }

    public List<ReworkTableView> getReworkTableViewList() {
        Thread thread = new Thread(){
            public synchronized void run(){
                test();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.run();
        return reworkTableViewList;
    }

    public void test(){
        while (true){
            reworkTableViewList = new ArrayList<>();
            for (int i = 1; i < 23; i++) {
                ReworkTableView reworkTableView = new ReworkTableView();
                reworkTableView.setSutureLine("VSEW00"+String.valueOf(i));
                int percentOfYesterday = randInt(0, 100);
                reworkTableView.setPercentOfYesterday(percentOfYesterday+"%");
                int percentOfToday = randInt(0, 100);
                reworkTableView.setPercentOfToday(percentOfToday+"%");
                reworkTableView.setTrends((percentOfYesterday - percentOfToday) +"%");
                reworkTableViewList.add(reworkTableView);
            }
            log.debug("DONE");
        }
    }
}
