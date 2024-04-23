package owners;

import cats.CatService;
import cats.entities.Cat;
import lombok.RequiredArgsConstructor;
import owners.dao.OwnerDao;
import owners.entities.Owner;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class OwnerService {
    private final OwnerDao ownerDao;
    private final CatService catService;

    public void saveOwner(Owner owner){
        ownerDao.saveOwner(owner);
    }

    public Owner findOwnerById(long id) throws NoSuchElementException {
        return Optional.ofNullable(ownerDao.findOwnerById(id))
                .orElseThrow(() -> new NoSuchElementException("Owner not found"));
    }

    public List<Owner> getAllOwners(){
        return ownerDao.getAllOwners();
    }

    public void deleteOwner(long id){
        Owner existingOwner = findOwnerById(id);
        ownerDao.deleteOwner(existingOwner.getId());

    }

    public void addCat(long ownerId, long catId) {
        Owner owner = findOwnerById(ownerId);
        Cat cat = catService.findCatById(catId);
        if (owner.getCats().contains(cat)) {
            throw new IllegalArgumentException("Owner already has this cat");
        }
        owner.getCats().add(cat);
        ownerDao.saveOwner(owner);
    }

    public void deleteCat(long ownerId, long catId) {
        Owner owner = findOwnerById(ownerId);
        Cat cat = catService.findCatById(catId);
        if (!owner.getCats().contains(cat)) {
            throw new IllegalArgumentException("Owner does not have this cat");
        }
        owner.getCats().remove(cat);
        ownerDao.saveOwner(owner);
    }

    public void deleteCat(long id){
        Owner existingOwner = findOwnerById(id);
        ownerDao.deleteOwner(existingOwner.getId());

    }
}
