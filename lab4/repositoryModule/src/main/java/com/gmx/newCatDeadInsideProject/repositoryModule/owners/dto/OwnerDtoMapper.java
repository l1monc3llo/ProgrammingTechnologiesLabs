package com.gmx.newCatDeadInsideProject.repositoryModule.owners.dto;

import com.gmx.newCatDeadInsideProject.repositoryModule.cats.entities.Cat;
import com.gmx.newCatDeadInsideProject.repositoryModule.cats.repositories.CatRepository;
import com.gmx.newCatDeadInsideProject.repositoryModule.owners.entities.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OwnerDtoMapper {
    private final CatRepository catRepository;
    public OwnerDto toDto(Owner owner) {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(owner.getOwner_id());
        ownerDto.setName(owner.getName());
        ownerDto.setBirth_date(owner.getBirth_date());
        ownerDto.setCatIds(owner.getCats().stream()
                .map(Cat::getCat_id)
                .collect(Collectors.toList()));
        return ownerDto;
    }

    public Owner toEntity(OwnerDto ownerDto) {
        Owner owner = new Owner();
        owner.setOwner_id(ownerDto.getId());
        owner.setName(ownerDto.getName());
        owner.setBirth_date(ownerDto.getBirth_date());

        List<Cat> cats = ownerDto.getCatIds().stream()
                .map(catId -> catRepository.findById(catId).orElseThrow(() -> new IllegalArgumentException("Invalid cat id: " + catId)))
                .toList();

        owner.setCats(cats);
        return owner;
    }

    public List<OwnerDto> toDtoList(List<Owner> owners) {
        return owners.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Owner> toEntityList(List<OwnerDto> ownerDtos) {
        return ownerDtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}