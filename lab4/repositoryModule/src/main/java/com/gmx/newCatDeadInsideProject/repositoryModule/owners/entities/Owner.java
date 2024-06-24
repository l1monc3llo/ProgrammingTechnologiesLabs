package com.gmx.newCatDeadInsideProject.repositoryModule.owners.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmx.newCatDeadInsideProject.repositoryModule.cats.entities.Cat;
import com.gmx.newCatDeadInsideProject.repositoryModule.users.entities.UserAcc;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_owner")
public class Owner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long owner_id;

    private String name;
    private LocalDate birth_date;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = false)
    @JsonIgnore
    private List<Cat> cats;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_acc")
    private UserAcc user_acc;
}