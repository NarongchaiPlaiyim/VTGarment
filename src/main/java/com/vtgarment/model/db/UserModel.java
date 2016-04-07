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
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private int id;

    @Column(name = "code", nullable = false, insertable = true, updatable = true, length = 2147483647)
    private String code;

    @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 2147483647)
    private String name;

    @Column(name = "username", nullable = false, insertable = true, updatable = true, length = 2147483647)
    private String username;

    @Column(name = "password", nullable = false, insertable = true, updatable = true, length = 2147483647)
    private String password;

    @Column(name = "isactive", nullable = true, insertable = true, updatable = true)
    private Boolean isactive;

    @Column(name = "user_image", nullable = true, insertable = true, updatable = true, length = 2147483647)
    private String userImage;

    @OneToOne
    @JoinColumn(name="line_id", nullable=false, columnDefinition="int default 0")
    private LineModel lineId;

    @Column(name = "section_id")
    private int sectionId;

    @Column(name = "factory_id")
    private int factoryId;
}
