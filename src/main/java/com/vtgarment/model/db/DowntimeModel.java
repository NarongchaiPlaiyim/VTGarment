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
@Table(name = "downtime", schema = "public")
public class DowntimeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "from_datetime", nullable = true, insertable = true, updatable = true)
    private Date fromDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "to_datetime", nullable = true, insertable = true, updatable = true)
    private Date toDatetime;

    @Column(name = "total_downtime", nullable = true, insertable = true, updatable = true)
    private int totalDowntime;

    @ManyToOne
    @JoinColumn(name = "downtime_cause_category_id", referencedColumnName = "id")
    private DowntimeCauseCategoryModel downtimeCauseCategoryByDowntimeCauseCategoryId;

    @ManyToOne
    @JoinColumn(name = "line_id", referencedColumnName = "id")
    private LineModel lineByLineId;

    @ManyToOne
    @JoinColumn(name = "production_id", referencedColumnName = "id")
    private ProductionModel productionByProductionId;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "last_update")
    private Date lastUpdate;
}
