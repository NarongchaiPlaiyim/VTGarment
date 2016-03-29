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
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private int id;

    @Column(name = "code", nullable = false, insertable = true, updatable = true, length = 2147483647)
    private String code;

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 2147483647)
    private String name;

    @ManyToOne
    @JoinColumn(name = "leader_id", referencedColumnName = "id", nullable = false)
    private UserModel userByLeaderId;

    @ManyToOne
    @JoinColumn(name = "building_floor_id", referencedColumnName = "id", nullable = false)
    private BuildingFloorModel buildingFloorModel;
}
