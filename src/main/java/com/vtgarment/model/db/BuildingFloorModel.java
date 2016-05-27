package com.vtgarment.model.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "building_floor", schema = "public")
public class BuildingFloorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, insertable = true, updatable = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "factory_id", referencedColumnName = "id", nullable = false)
    private FactoryModel factoryByFactoryId;
}
