package com.vtgarment.model.view.otp;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Created by pakorn on 01/04/2016.
 */
@Getter
@Setter
public class OtpTableView {
    private String lineCode;
    private BigDecimal toDay;
    private String styleToDay;
    private BigDecimal yesterDay;
    private String styleYesterDay;
    private BigDecimal trend;
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
