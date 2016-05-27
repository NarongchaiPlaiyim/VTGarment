package com.vtgarment.model.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "line", schema = "public")
public class LineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code", nullable = false, insertable = true, updatable = true)
    private String code;

    @Column(name = "name", nullable = false, insertable = true, updatable = true)
    private String name;

    @Column(name = "leader_id")
    private int leaderId;

    @ManyToOne
    @JoinColumn(name = "building_floor_id", referencedColumnName = "id", nullable = false)
    private BuildingFloorModel buildingFloorModel;

    @Column(name="ipaddress")
    private String ipAddress;

    @Column(name="user")
    private String user;

    @Column(name="password")
    private String password;
}
