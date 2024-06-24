package com.gmx.newCatDeadInsideProject.repositoryModule.cats.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gmx.newCatDeadInsideProject.repositoryModule.cats.models.CatBreed;
import com.gmx.newCatDeadInsideProject.repositoryModule.cats.models.CatColor;
import com.gmx.newCatDeadInsideProject.repositoryModule.owners.entities.Owner;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_cat")
public class Cat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cat_id;
    private String name;
    private LocalDate birth_date;
    @Enumerated(EnumType.STRING)
    private CatBreed breed;
    @Enumerated(EnumType.STRING)
    private CatColor color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "_cat_friends",
            joinColumns = @JoinColumn(name = "cat_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    @JsonIgnore
    private List<Cat> friends = new ArrayList<>();
}
