package cats;

import cats.dto.CatDto;
import cats.dto.CatDtoMapper;
import cats.entities.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cats")
public class CatController {

    private final CatService catService;
    private final CatDtoMapper catDtoMapper;

    @Autowired
    public CatController(CatService catService, CatDtoMapper catDtoMapper) {
        this.catService = catService;
        this.catDtoMapper = catDtoMapper;
    }

    @PostMapping
    public void saveCat(@RequestBody CatDto catDto) {
        Cat cat = catDtoMapper.toEntity(catDto);
        catService.saveCat(cat);
    }

    @GetMapping("/{id}")
    public CatDto getCatById(@PathVariable long id) {
        Cat cat = catService.findCatById(id);
        return catDtoMapper.toDto(cat);
    }

    @GetMapping
    public List<CatDto> findAllCats() {
        List<Cat> cats = catService.findAllCats();
        return cats.stream()
                .map(catDtoMapper::toDto)
                .collect(Collectors.toList());
    }

    @DeleteMapping("/{id}")
    public void deleteCat(@PathVariable long id) {
        catService.deleteCat(id);
    }

    @PostMapping("/{catId}/friends/{friendId}")
    public void addFriend(@PathVariable long catId, @PathVariable long friendId) {
        catService.addFriend(catId, friendId);
    }

    @DeleteMapping("/{catId}/friends/{friendId}")
    public void deleteFriend(@PathVariable long catId, @PathVariable long friendId) {
        catService.deleteFriend(catId, friendId);
    }
}