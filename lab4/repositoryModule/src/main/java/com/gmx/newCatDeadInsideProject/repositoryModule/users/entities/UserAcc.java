package com.gmx.newCatDeadInsideProject.repositoryModule.users.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmx.newCatDeadInsideProject.repositoryModule.owners.entities.Owner;
import com.gmx.newCatDeadInsideProject.repositoryModule.users.models.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "_user")
public class UserAcc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;

    private String username;

    private String password;

    @ManyToMany
    @JoinTable(name = "_users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user_acc")
    @JsonIgnore
    private Owner owner;

    public UserAcc(String username, String password) {
        this.username = username;
        this.password = password;
        this.roles.add(new Role("ROLE_USER"));
    }

    public UserAcc(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }
}