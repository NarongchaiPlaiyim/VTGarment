package com.vtgarment.service.security;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
public class UserDetail implements Serializable {
    private int id;
    private String code;
    private String name;
    private int lineId;
    private int sectionId;
    private String userName;
    private int factoryId;

    public UserDetail(int id, String code, String name, int lineId, int sectionId, String userName, int factoryId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.lineId = lineId;
        this.sectionId = sectionId;
        this.userName = userName;
        this.factoryId = factoryId;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("code", code)
                .append("name", name)
                .append("lineId", lineId)
                .append("sectionId", sectionId)
                .append("userName", userName)
                .append("factoryId", factoryId)
                .toString();
    }
}
