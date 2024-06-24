package com.gmx.newCatDeadInsideProject.repositoryModule.cats.dto;

import com.gmx.newCatDeadInsideProject.repositoryModule.cats.entities.Cat;
import com.gmx.newCatDeadInsideProject.repositoryModule.cats.repositories.CatRepository;
import com.gmx.newCatDeadInsideProject.repositoryModule.owners.entities.Owner;
import com.gmx.newCatDeadInsideProject.repositoryModule.owners.repositories.OwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CatDtoMapper {

    private final CatRepository catRepository;
    private final OwnerRepository ownerRepository;

    public CatDto toDto(Cat cat) {
        CatDto catDto = new CatDto();
        catDto.setId(cat.getCat_id());
        catDto.setName(cat.getName());
        catDto.setBirth_date(cat.getBirth_date());
        Owner owner = cat.getOwner();
        catDto.setOwnerId(owner != null ? owner.getOwner_id() : 0);
        catDto.setBreed(cat.getBreed());
        catDto.setColor(cat.getColor());
        catDto.setFriendIds(cat.getFriends() != null
                ? cat.getFriends().stream().map(Cat::getCat_id).collect(Collectors.toList())
                : Collections.emptyList());
        return catDto;
    }

    public List<CatDto> toDtoList(List<Cat> cats) {
        return cats.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Cat toEntity(CatDto catDto) {
        Cat cat = new Cat();
        cat.setCat_id(catDto.getId());
        cat.setName(catDto.getName());
        cat.setBirth_date(catDto.getBirth_date());
        cat.setOwner(catDto.getOwnerId() != 0 ? ownerRepository.findById(catDto.getOwnerId()).orElse(null) : null);
        cat.setBreed(catDto.getBreed());
        cat.setColor(catDto.getColor());
        cat.setFriends(catDto.getFriendIds() != null
                ? catDto.getFriendIds().stream().map(id -> catRepository.findById(id).orElse(null)).filter(Objects::nonNull).collect(Collectors.toList())
                : Collections.emptyList());
        return cat;
    }

    public List<Cat> toEntityList(List<Long> catIds) {
        return catIds.stream()
                .map(id -> catRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
