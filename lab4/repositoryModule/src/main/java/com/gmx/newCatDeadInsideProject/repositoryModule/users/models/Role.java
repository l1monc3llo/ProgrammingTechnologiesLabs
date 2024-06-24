package com.gmx.newCatDeadInsideProject.repositoryModule.users.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@NoArgsConstructor
@Table(name = "_role")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long role_id;
    @Column(unique = true, nullable = false)
    private String name;
    public Role(String name) {
        this.name = name;
    }
}
