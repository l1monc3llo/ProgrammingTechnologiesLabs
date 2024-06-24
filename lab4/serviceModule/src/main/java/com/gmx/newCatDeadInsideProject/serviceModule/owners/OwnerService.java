package com.gmx.newCatDeadInsideProject.serviceModule.owners;

import com.gmx.newCatDeadInsideProject.repositoryModule.cats.entities.Cat;
import com.gmx.newCatDeadInsideProject.repositoryModule.owners.entities.Owner;
import com.gmx.newCatDeadInsideProject.repositoryModule.owners.repositories.OwnerRepository;
import com.gmx.newCatDeadInsideProject.serviceModule.cats.CatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final CatService catService;

    public void saveOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    public Owner findOwnerById(Long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Owner not found"));
    }

    public List<Owner> findAllOwners() {
        return ownerRepository.findAll();
    }

    public void deleteOwnerById(Long id) {
        Owner existingOwner = findOwnerById(id);
        ownerRepository.delete(existingOwner);
    }

    @Transactional
    public void addCatToOwner(Long ownerId, Long catId) {
        Owner owner = findOwnerById(ownerId);
        Cat cat = catService.findCatById(catId);
        if (owner.getCats().contains(cat)) {
            throw new IllegalArgumentException("Cat already belongs to the owner");
        }
        owner.getCats().add(cat);
        cat.setOwner(owner);
    }

    @Transactional
    public void deleteCatFromOwner(Long ownerId, Long catId) {
        Owner owner = findOwnerById(ownerId);
        Cat cat = catService.findCatById(catId);
        if (!owner.getCats().contains(cat)) {
            throw new IllegalArgumentException("Cat doesn't belong to the owner");
        }
        owner.getCats().remove(cat);
        cat.setOwner(null);
    }
}
