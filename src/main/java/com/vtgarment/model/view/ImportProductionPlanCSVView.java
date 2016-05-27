package com.vtgarment.model.view;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by pakor on 27/05/2016.
 */
@Getter
@Setter
@ToString
public class ImportProductionPlanCSVView {
    private String Line;
    private String Style;
    private String CO;
    private String Qty;
    private String Shipment;
}
