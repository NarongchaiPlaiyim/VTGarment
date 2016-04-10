package com.vtgarment.model.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@ToString
@Entity
@Table(name = "production", schema = "public")
public class ProductionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private int id;

    @Column(name = "co", nullable = true, insertable = true, updatable = true, length = 2147483647)
    private String co;

    @Column(name = "style", nullable = true, insertable = true, updatable = true, length = 2147483647)
    private String style;

    @Column(name = "qty", nullable = true, insertable = true, updatable = true)
    private int qty;

    @Column(name = "plan_per_day", nullable = true, insertable = true, updatable = true)
    private int planPerDay;

    @Column(name = "takttime", nullable = true, insertable = true, updatable = true, precision = 8)
    private float takttime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "plan_start")
    private Date planStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "plan_finish")
    private Date planFinish;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "actual_start")
    private Date actualStart;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "actual_finish")
    private Date actualFinish;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "shipment")
    private Date shipmen;

    @Column(name = "sew_otp_target", nullable = true, insertable = true, updatable = true, precision = 8)
    private float sewOtpTarget;

    @Column(name = "sew_otp_actual", nullable = true, insertable = true, updatable = true, precision = 8)
    private float sewOtpActual;

    @Column(name = "pack_otp_target", nullable = true, insertable = true, updatable = true, precision = 8)
    private float packOtpTarget;

    @Column(name = "pack_otp_actual", nullable = true, insertable = true, updatable = true, precision = 8)
    private float packOtpActual;

    @Column(name = "rework_target", nullable = true, insertable = true, updatable = true)
    private int reworkTarget;

    @Column(name = "rework_actual", nullable = true, insertable = true, updatable = true)
    private int reworkActual;

    @Column(name = "wait_iron_carry", nullable = true, insertable = true, updatable = true)
    private int waitIronCarry;

    @Column(name = "wait_qc_carry", nullable = true, insertable = true, updatable = true)
    private int waitQcCarry;

    @Column(name = "wait_pack_carry", nullable = true, insertable = true, updatable = true)
    private int waitPackCarry;

    @Column(name = "wait_rework_carry", nullable = true, insertable = true, updatable = true)
    private int waitReworkCarry;

    @OneToOne
    @JoinColumn(name = "id")
    private SamModel samBySamId;

    @OneToOne
    @JoinColumn(name = "id")
    private SystemStatusModel systemStatusByStatusId;

    @Column(name = "rework_qty_target")
    private int reworkQtyTarget;

    @Column(name = "rework_qty_actual")
    private int reworkQtyActual;

    @Column(name = "downtime_man")
    private Double downtimeMan;

    @Column(name = "downtime_mach", nullable=false, columnDefinition="int default 0")
    private int downtimeMach;

    @Column(name = "downtime_method", nullable=false, columnDefinition="int default 0")
    private int downtimeMethod;

    @Column(name = "downtime_material", nullable=false, columnDefinition="int default 0")
    private int downtimeMaterial;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate;

}
