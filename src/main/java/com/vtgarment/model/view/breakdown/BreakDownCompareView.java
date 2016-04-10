package com.vtgarment.model.view.breakdown;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by pakorn on 10/04/2016.
 */
@Getter
@Setter
public class BreakDownCompareView {
    private int man;
    private int mach;
    private int method;
    private int material;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("BreakDownCompareView{");
        sb.append("man=").append(man);
        sb.append(", mach=").append(mach);
        sb.append(", method=").append(method);
        sb.append(", material=").append(material);
        sb.append('}');
        return sb.toString();
    }
}
