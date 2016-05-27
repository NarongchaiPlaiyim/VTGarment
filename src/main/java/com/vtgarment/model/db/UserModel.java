package com.vtgarment.model.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Entity
@Table(name = "user", schema = "public")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "code", nullable = false, insertable = true, updatable = true)
    private String code;

    @Column(name = "name", nullable = false, insertable = true, updatable = true)
    private String name;

    @Column(name = "username", nullable = false, insertable = true, updatable = true)
    private String username;

    @Column(name = "password", nullable = false, insertable = true, updatable = true)
    private String password;

    @Column(name = "isactive", nullable = true, insertable = true, updatable = true)
    private Boolean isactive;

    @Column(name = "user_image", nullable = true, insertable = true, updatable = true)
    private String userImage;

    @Column(name = "factory_id")
    private int factoryId;
}
