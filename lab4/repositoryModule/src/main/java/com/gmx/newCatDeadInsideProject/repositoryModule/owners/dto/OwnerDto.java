package com.gmx.newCatDeadInsideProject.repositoryModule.owners.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class OwnerDto {
    private long id;
    private String name;
    private LocalDate birth_date;
    private List<Long> catIds = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDto ownerDto = (OwnerDto) o;
        return Objects.equals(id, ownerDto.id) &&
                Objects.equals(name, ownerDto.name) &&
                Objects.equals(birth_date, ownerDto.birth_date) &&
                Objects.equals(catIds, ownerDto.catIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, birth_date, catIds);
    }
}