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
    private int leaderId;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDetail{");
        sb.append("id=").append(id);
        sb.append(", code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", lineId=").append(lineId);
        sb.append(", sectionId=").append(sectionId);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", factoryId=").append(factoryId);
        sb.append(", role='").append(role).append('\'');
        sb.append(", leaderId=").append(leaderId);
        sb.append('}');
        return sb.toString();
    }

    public UserDetail(int id, String code, String name, int lineId, int sectionId, String userName, int factoryId, int leaderId) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.lineId = lineId;
        this.sectionId = sectionId;
        this.userName = userName;
        this.factoryId = factoryId;
        this.leaderId = leaderId;
    }
}
