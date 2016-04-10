package com.vtgarment.model.view.outstading;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by pakorn on 03/04/2016.
 */
@Getter
@Setter
public class OutStadingTableView {
    private String lineCode;
    private int toDay;
    private String styleToDay;
    private int yesterDay;
    private String styleYesterDay;
    private int trend;
    private String image;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OtpTableView{");
        sb.append("lineCode='").append(lineCode).append('\'');
        sb.append(", toDay=").append(toDay);
        sb.append(", styleToDay='").append(styleToDay).append('\'');
        sb.append(", yesterDay=").append(yesterDay);
        sb.append(", styleYesterDay='").append(styleYesterDay).append('\'');
        sb.append(", trend=").append(trend);
        sb.append(", image='").append(image).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
