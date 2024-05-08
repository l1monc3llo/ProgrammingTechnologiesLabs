package owners;

import cats.CatService;
import cats.entities.Cat;
import owners.entities.Owner;
import owners.repositories.OwnerRepository;

import java.util.List;
import java.util.NoSuchElementException;

public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final CatService catService;

    public OwnerService(OwnerRepository ownerRepository, CatService catService) {
        this.ownerRepository = ownerRepository;
        this.catService = catService;
    }

    public void saveOwner(Owner owner) {
        ownerRepository.save(owner);
    }

    public Owner findOwnerById(long id) {
        return ownerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Owner not found"));
    }

    public List<Owner> findAllOwners() {
        return ownerRepository.findAll();
    }

    public void deleteOwnerById(long id) {
        Owner existingOwner = findOwnerById(id);
        ownerRepository.delete(existingOwner);
    }

    public void addCatToOwner(long ownerId, long catId) {
        Owner owner = findOwnerById(ownerId);
        Cat cat = catService.findCatById(catId);
        if (owner.getCats() == null || !owner.getCats().contains(cat)) {
            owner.getCats().add(cat);
            ownerRepository.save(owner);
        }
    }

    public void deleteCatFromOwner(long ownerId, long catId) {
        Owner owner = findOwnerById(ownerId);
        Cat cat = catService.findCatById(catId);
        if (owner.getCats() != null && owner.getCats().contains(cat)) {
            owner.getCats().remove(cat);
            ownerRepository.save(owner);
        }
    }
}