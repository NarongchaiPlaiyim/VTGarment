package com.vtgarment.service.security;

import com.vtgarment.model.db.UserLineModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public class UserDetail implements Serializable {
    private int id;
    private String code;
    private String name;
    private String userName;
    private int factoryId;
    private String role = "USER";
    private String lineId;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserDetail{");
        sb.append("id=").append(id);
        sb.append(", code='").append(code).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", factoryId=").append(factoryId);
        sb.append(", role='").append(role).append('\'');
        sb.append(", lineId='").append(lineId).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public UserDetail(int id, String code, String name, String userName, int factoryId, List<UserLineModel> userLineModels) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.userName = userName;
        this.factoryId = factoryId;

        StringBuilder line = new StringBuilder();
        for (int i=0; i<userLineModels.size(); i++){
            line.append(String.valueOf(userLineModels.get(i).getLineId()));

            if (i+1 < userLineModels.size()){
                line.append(",");
            }
        }

        this.lineId = line.toString();
    }
}
