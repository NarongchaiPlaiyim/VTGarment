package com.vtgarment.model.view.System;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Created by pakor on 24/05/2016.
 */
@Setter
@Getter
@ToString
public class ImportProductionPlanView {
    private String line;
    private String style;
    private String co;
    private String qty;
    private String shipment;

    public ImportProductionPlanView() {
    }

    public ImportProductionPlanView(String line, String style, String co, String qty, String shipment) {
        this.line = line;
        this.style = style;
        this.co = co;
        this.qty = qty;
        this.shipment = shipment;
    }
}
