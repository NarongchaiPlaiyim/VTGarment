package com.vtgarment.model.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by pakor on 27/05/2016.
 */
@Getter
@Setter
@ToString
@Entity
@Table(name = "production_plan", schema = "public")
public class ProductionPlanModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="line", nullable = true, insertable = true, updatable = true)
    private String line;

    @Column(name="style", nullable = true, insertable = true)
    private String style;

    @Column(name="co", nullable = true, insertable = true)
    private String co;

    @Column(name="qty", columnDefinition="int default 0")
    private int qty;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "shipment")
    private Date shipment;
}
