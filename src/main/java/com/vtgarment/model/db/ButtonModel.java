package com.vtgarment.model.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "button", schema = "public", catalog = "visualboardDB")
public class ButtonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private int id;

    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 2147483647)
    private String name;

    @Column(name = "description", nullable = true, insertable = true, updatable = true, length = 2147483647)
    private String description;
}
