package com.vtgarment.model.view.rework;

import com.vtgarment.model.view.View;
import com.vtgarment.utils.DateUtil;
import com.vtgarment.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
@ToString
public class ReworkView extends View {
    private String previousDate;
    private String currentDate;
    private List<ReworkTableView> reworkTableViewList;

    public ReworkView() {
        previousDate = DateUtil.convertDateToString_DD_MM_YYYY(Utils.previousDate());
        currentDate = DateUtil.convertDateToString_DD_MM_YYYY(Utils.currentDate());
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
    }

    public int randInt(int min, int max) {
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }
}
