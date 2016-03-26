package com.vtgarment.model.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "building_floor", schema = "public", catalog = "visualboardDB")
public class BuildingFloorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private int id;

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 2147483647)
    private String name;

    @ManyToOne
    @JoinColumn(name = "factory_id", referencedColumnName = "id", nullable = false)
    private FactoryModel factoryByFactoryId;
}
