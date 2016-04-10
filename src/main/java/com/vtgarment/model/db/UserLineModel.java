package com.vtgarment.model.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user_line", schema = "public")
public class UserLineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private int id;

    @Column(name="user_id")
    private int userId;

    @Column(name = "line_id")
    private int lineId;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserLineModel{");
        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", lineId=").append(lineId);
        sb.append('}');
        return sb.toString();
    }
}
