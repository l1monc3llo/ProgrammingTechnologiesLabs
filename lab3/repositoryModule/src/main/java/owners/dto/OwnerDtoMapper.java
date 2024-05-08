package owners.dto;

import cats.dto.CatDto;
import cats.dto.CatDtoMapper;
import cats.repositories.CatRepository;
import owners.entities.Owner;

import java.util.List;
import java.util.stream.Collectors;

public class OwnerDtoMapper {
    private final CatDtoMapper catDtoMapper;

    public OwnerDtoMapper(CatDtoMapper catDtoMapper) {
        this.catDtoMapper = catDtoMapper;
    }

    public OwnerDto toDto(Owner owner) {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(owner.getId());
        ownerDto.setName(owner.getName());
        ownerDto.setBirthdate(owner.getBirthdate());
        ownerDto.setCats(catDtoMapper.toDtoList(owner.getCats()));
        return ownerDto;
    }

    public Owner toEntity(OwnerDto ownerDto) {
        Owner owner = new Owner();
        owner.setId(ownerDto.getId());
        owner.setName(ownerDto.getName());
        owner.setBirthdate(ownerDto.getBirthdate());

        List<Long> catIds = ownerDto.getCats().stream()
                .map(CatDto::getId)
                .collect(Collectors.toList());

        owner.setCats(catDtoMapper.toEntityList(catIds));
        return owner;
    }

    public List<OwnerDto> toDtoList(List<Owner> owners) {
        return owners.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Owner> toEntityList(List<OwnerDto> ownerDtos, CatRepository catRepository) {
        return ownerDtos.stream()
                .map(ownerDto -> toEntity(ownerDto))
                .collect(Collectors.toList());
    }
}