package com.vtgarment.service.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class UserDetail implements Serializable {
    private int id;
    private String code;
    private String name;
    private int lineId;
    private int sectionId;
    private String userName;
    private int factoryId;
    private String role = "USER";

    public UserDetail(int id, String code, String name, int lineId, int sectionId, String userName, int factoryId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.lineId = lineId;
        this.sectionId = sectionId;
        this.userName = userName;
        this.factoryId = factoryId;
    }
}
