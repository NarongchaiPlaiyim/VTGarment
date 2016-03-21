package com.vtgarment.model.view.rework;

import com.vtgarment.model.view.View;
import com.vtgarment.utils.DateUtil;
import com.vtgarment.utils.Utils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReworkView extends View {

    private String previousDate;
    private String currentDate;

    public ReworkView() {
        previousDate = DateUtil.convertDateToString_DD_MM_YYYY(Utils.previousDate());
        currentDate = DateUtil.convertDateToString_DD_MM_YYYY(Utils.currentDate());
    }


}
