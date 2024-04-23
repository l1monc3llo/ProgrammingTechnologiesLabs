package owners;

import owners.entities.Owner;

import java.util.List;

public class OwnerController {
    private final OwnerService ownerService;

    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    public List<Owner> getAllOwners() {
        return ownerService.getAllOwners();
    }

    public Owner getOwnerById(long id) {
        Owner owner = ownerService.findOwnerById(id);
        if (owner == null) {
            throw new IllegalArgumentException("Owner not found");
        }
        return owner;
    }

    public String addOwner(Owner owner) {
        ownerService.saveOwner(owner);
        return "Owner added successfully";
    }

    public String deleteOwner(long id) {
        ownerService.deleteOwner(id);
        return "Owner deleted successfully";
    }
}
