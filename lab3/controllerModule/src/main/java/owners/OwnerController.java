package owners;

import owners.dto.OwnerDto;
import owners.dto.OwnerDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import owners.entities.Owner;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final OwnerService ownerService;
    private final OwnerDtoMapper ownerDtoMapper;

    @Autowired
    public OwnerController(OwnerService ownerService, OwnerDtoMapper ownerDtoMapper) {
        this.ownerService = ownerService;
        this.ownerDtoMapper = ownerDtoMapper;
    }

    @PostMapping
    public void saveOwner(@RequestBody OwnerDto ownerDto) {
        Owner owner = ownerDtoMapper.toEntity(ownerDto);
        ownerService.saveOwner(owner);
    }

    @GetMapping("/{id}")
    public OwnerDto getOwnerById(@PathVariable long id) {
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
    public void deleteOwner(@PathVariable long id) {
        ownerService.deleteOwnerById(id);
    }

    @PutMapping("/{ownerId}/cats/{catId}")
    public void addCatToOwner(@PathVariable long ownerId, @PathVariable long catId) {
        ownerService.addCatToOwner(ownerId, catId);
    }

    @DeleteMapping("/{ownerId}/cats/{catId}")
    public void deleteCatFromOwner(@PathVariable long ownerId, @PathVariable long catId) {
        ownerService.deleteCatFromOwner(ownerId, catId);
    }
}
