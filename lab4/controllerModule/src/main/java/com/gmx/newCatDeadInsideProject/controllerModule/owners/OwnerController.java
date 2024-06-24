package com.gmx.newCatDeadInsideProject.controllerModule.owners;


import com.gmx.newCatDeadInsideProject.repositoryModule.owners.dto.OwnerDto;
import com.gmx.newCatDeadInsideProject.repositoryModule.owners.dto.OwnerDtoMapper;
import com.gmx.newCatDeadInsideProject.repositoryModule.owners.entities.Owner;
import com.gmx.newCatDeadInsideProject.serviceModule.owners.OwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerDtoMapper ownerDtoMapper;


    @PostMapping
    public void saveOwner(@RequestBody OwnerDto ownerDto) {
        Owner owner = ownerDtoMapper.toEntity(ownerDto);
        ownerService.saveOwner(owner);
    }

    @GetMapping("/{id}")
    public OwnerDto getOwnerById(@PathVariable Long id) {
        Owner owner = ownerService.findOwnerById(id);
        return ownerDtoMapper.toDto(owner);
    }

    @GetMapping
    public List<OwnerDto> getAllOwners() {
        List<Owner> owners = ownerService.findAllOwners();
        return owners.stream()
                .map(ownerDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteOwner(@PathVariable Long id) {
        ownerService.deleteOwnerById(id);
    }

    @PostMapping("/{ownerId}/cats/{catId}")
    public void addCatToOwner(@PathVariable Long ownerId, @PathVariable Long catId) {
        ownerService.addCatToOwner(ownerId, catId);
    }

    @DeleteMapping("/{ownerId}/cats/{catId}")
    public void deleteCatFromOwner(@PathVariable Long ownerId, @PathVariable Long catId) {
        ownerService.deleteCatFromOwner(ownerId, catId);
    }
}
