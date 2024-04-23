import cats.CatService;
import owners.OwnerService;
import owners.dao.OwnerDao;
import owners.entities.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OwnerServiceTest {

    private OwnerService ownerService;

    @Mock
    private OwnerDao ownerDao;

    @Mock
    private CatService catService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        ownerService = new OwnerService(ownerDao, catService);
    }


    @Test
    public void testSaveOwner() {
        Owner owner = new Owner();
        ownerService.saveOwner(owner);
        verify(ownerDao, times(1)).saveOwner(owner);
    }

    @Test
    public void testFindOwnerById() {
        Owner owner = new Owner();
        when(ownerDao.findOwnerById(1L)).thenReturn(owner);

        Owner foundOwner = ownerService.findOwnerById(1L);
        assertEquals(owner, foundOwner);
    }

    @Test
    public void testGetAllOwners() {
        Owner owner1 = new Owner();
        Owner owner2 = new Owner();
        List<Owner> owners = Arrays.asList(owner1, owner2);
        when(ownerDao.getAllOwners()).thenReturn(owners);

        List<Owner> foundOwners = ownerService.getAllOwners();
        assertEquals(2, foundOwners.size());
        assertEquals(owner1, foundOwners.get(0));
        assertEquals(owner2, foundOwners.get(1));
    }

    @Test
    public void testDeleteOwner() {
        Owner owner = new Owner();
        owner.setId(1L);
        when(ownerDao.findOwnerById(1L)).thenReturn(owner);

        ownerService.deleteOwner(1L);
        verify(ownerDao, times(1)).deleteOwner(1L);
    }
}
