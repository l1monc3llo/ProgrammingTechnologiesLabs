import cats.CatService;
import cats.dao.CatDao;
import cats.entities.Cat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.NoSuchElementException;

public class CatServiceTest {

    private final CatDao dao = Mockito.mock(CatDao.class);
    private final CatService service = new CatService(dao);

    @Test
    public void shouldFindById() {
        Cat cat = new Cat();
        cat.setId(1L);
        Mockito.when(dao.findCatById(1L)).thenReturn(cat);

        Cat found = service.findCatById(1L);

        Assertions.assertEquals(1L, found.getId());
    }

    @Test
    public void shouldFindAllCats() {
        Mockito.when(dao.getAllCats()).thenReturn(List.of(new Cat(), new Cat()));

        List<Cat> cats = service.getAllCats();

        Assertions.assertEquals(2, cats.size());
    }

    @Test
    public void shouldThrowExceptionWhenFindingNonExistingCatById() {
        Mockito.when(dao.findCatById(1L)).thenReturn(null);

        Assertions.assertThrows(NoSuchElementException.class, () -> service.findCatById(1L));
    }

    @Test
    public void shouldDeleteCat() {
        Cat cat = new Cat();
        cat.setId(1L);
        Mockito.when(dao.findCatById(1L)).thenReturn(cat);

        service.deleteCat(1L);

        Mockito.verify(dao, Mockito.times(1)).deleteCat(1L);
    }

    @Test
    public void shouldAddFriend() {
        Cat cat1 = new Cat();
        cat1.setId(1L);
        Cat cat2 = new Cat();
        cat2.setId(2L);
        Mockito.when(dao.findCatById(1L)).thenReturn(cat1);
        Mockito.when(dao.findCatById(2L)).thenReturn(cat2);

        service.addFriend(1L, 2L);

        Assertions.assertTrue(cat1.getFriends().contains(cat2));
        Assertions.assertTrue(cat2.getFriends().contains(cat1));
        Mockito.verify(dao, Mockito.times(1)).saveCat(cat1);
        Mockito.verify(dao, Mockito.times(1)).saveCat(cat2);
    }
}
