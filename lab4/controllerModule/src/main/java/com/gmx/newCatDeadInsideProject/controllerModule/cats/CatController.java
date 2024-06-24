package com.gmx.newCatDeadInsideProject.controllerModule.cats;

import com.gmx.newCatDeadInsideProject.repositoryModule.cats.dto.CatDto;
import com.gmx.newCatDeadInsideProject.repositoryModule.cats.dto.CatDtoMapper;
import com.gmx.newCatDeadInsideProject.repositoryModule.cats.entities.Cat;
import com.gmx.newCatDeadInsideProject.serviceModule.cats.CatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cats")

public class CatController {

    private final CatService catService;
    private final CatDtoMapper catDtoMapper;

    @PostMapping
    public void saveCat(@RequestBody CatDto catDto) {
        Cat cat = catDtoMapper.toEntity(catDto);
        catService.saveCat(cat);
    }

    @GetMapping("/{id}")
    public CatDto getCatById(@PathVariable Long id) {
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
    public void deleteCat(@PathVariable Long id) {
        catService.deleteCat(id);
    }

    @PostMapping("/{catId}/friends/{friendId}")
    public void addFriend(@PathVariable Long catId, @PathVariable Long friendId) {
        catService.addFriend(catId, friendId);
    }

    @DeleteMapping("/{catId}/friends/{friendId}")
    public void deleteFriend(@PathVariable Long catId, @PathVariable Long friendId) {
        catService.deleteFriend(catId, friendId);
    }
}