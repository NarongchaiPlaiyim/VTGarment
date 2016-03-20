package com.vtgarment.model.view.rework;

import com.vtgarment.model.view.View;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class ReworkView extends View {
    private List<ReworkTableView> reworkTableViewList;

    public ReworkView() {
        reworkTableViewList = new ArrayList<>();
        for (int i = 0; i < 23; i++) {
            ReworkTableView reworkTableView = new ReworkTableView();
            reworkTableView.setSutureLine(String.valueOf(i));
            reworkTableView.setPercentOfYesterday(String.valueOf(i));
            reworkTableView.setPercentOfToday(String.valueOf(i));
            reworkTableView.setTrends(String.valueOf(i));
            reworkTableViewList.add(reworkTableView);
        }
    }
}
