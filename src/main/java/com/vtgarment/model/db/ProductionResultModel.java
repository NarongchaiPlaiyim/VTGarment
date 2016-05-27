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
@Table(name = "production_result", schema = "public")
public class ProductionResultModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "from_datetime", nullable = true, insertable = true, updatable = true)
    private Date fromDatetime;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "to_datetime", nullable = true, insertable = true, updatable = true)
    private Date toDatetime;

    @Column(name = "qty", nullable = true, insertable = true, updatable = true)
    private Integer qty;

    @ManyToOne
    @JoinColumn(name = "line_id", referencedColumnName = "id")
    private ProductionResultModel productionResultByLineId;
}
