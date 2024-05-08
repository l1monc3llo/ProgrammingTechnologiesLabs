import cats.CatController;
import cats.CatService;
import cats.entities.Cat;
import cats.dto.CatDto;
import cats.dto.CatDtoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CatControllerTest {

    @Mock
    private CatService catService;

    @Mock
    private CatDtoMapper catDtoMapper;

    @InjectMocks
    private CatController catController;

    @Test
    public void shouldSaveCat() {
        CatDto catDto = new CatDto();
        catDto.setId(1L);
        Cat cat = new Cat();
        when(catDtoMapper.toEntity(catDto)).thenReturn(cat);
        catController.saveCat(catDto);
        verify(catService, times(1)).saveCat(cat);
    }

    @Test
    public void shouldReturnCatDto() {
        long id = 1L;
        Cat cat = new Cat();
        CatDto catDto = new CatDto();
        when(catService.findCatById(id)).thenReturn(cat);
        when(catDtoMapper.toDto(cat)).thenReturn(catDto);
        CatDto result = catController.getCatById(id);
        assertEquals(catDto, result);
    }


    @Test
    public void shouldDeleteCat() {
        long id = 1L;
        catController.deleteCat(id);
        verify(catService, times(1)).deleteCat(id);
    }

    @Test
    public void shouldAddFriend() {
        long catId = 1L;
        long friendId = 2L;
        catController.addFriend(catId, friendId);
        verify(catService, times(1)).addFriend(catId, friendId);
    }

    @Test
    public void shouldDeleteFriend() {
        long catId = 1L;
        long friendId = 2L;
        catController.deleteFriend(catId, friendId);
        verify(catService, times(1)).deleteFriend(catId, friendId);
    }
}