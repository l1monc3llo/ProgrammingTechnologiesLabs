package com.gmx.newCatDeadInsideProject.repositoryModule.cats.dto;

import com.gmx.newCatDeadInsideProject.repositoryModule.cats.models.CatBreed;
import com.gmx.newCatDeadInsideProject.repositoryModule.cats.models.CatColor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class CatDto {
    private long id;
    private String name;
    private LocalDate birth_date;
    private long ownerId;
    private CatBreed breed;
    private CatColor color;
    private List<Long> friendIds = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatDto catDto = (CatDto) o;
        return Objects.equals(id, catDto.id) &&
                Objects.equals(name, catDto.name) &&
                Objects.equals(birth_date, catDto.birth_date) &&
                Objects.equals(ownerId, catDto.ownerId) &&
                Objects.equals(breed, catDto.breed) &&
                Objects.equals(color, catDto.color) &&
                Objects.equals(friendIds, catDto.friendIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birth_date, ownerId, breed, color, friendIds);
    }

}