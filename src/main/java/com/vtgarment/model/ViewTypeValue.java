package com.vtgarment.model;

import lombok.Getter;

@Getter
public enum ViewTypeValue {

    OTP(1, "OTP"),
    REWORK(2, "Rework"),
    BREAK_DOWN(3, "Break Down"),
    OUTSTANDING(4, "OutStanding");

    private int id;
    private String name;

    ViewTypeValue(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
