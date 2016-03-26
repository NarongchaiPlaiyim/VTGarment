package com.vtgarment.model.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "system_status", schema = "public", catalog = "visualboardDB")
public class SystemStatusModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private int id;

    @Column(name = "status", nullable = true, insertable = true, updatable = true, length = 2147483647)
    private String status;
}
