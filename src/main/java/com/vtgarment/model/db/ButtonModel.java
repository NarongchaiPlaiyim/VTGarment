package com.vtgarment.model.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "button", schema = "public")
public class ButtonModel {
    @Id

    private int id;

    @Column(name = "name", nullable = true, insertable = true, updatable = true)
    private String name;

    @Column(name = "description", nullable = true, insertable = true, updatable = true)
    private String description;
}
