package com.gmx.newCatDeadInsideProject.serviceModule.cats;


import com.gmx.newCatDeadInsideProject.repositoryModule.cats.entities.Cat;
import com.gmx.newCatDeadInsideProject.repositoryModule.cats.repositories.CatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
public class CatService {
    private final CatRepository catRepository;

    public void saveCat(Cat cat){
        catRepository.save(cat);
    }

    public Cat findCatById(Long id) throws NoSuchElementException {
        return catRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cat not found"));
    }

    public List<Cat> findAllCats(){
        return catRepository.findAll();
    }

    public void deleteCat(Long id){
        Cat existingCat = findCatById(id);
        catRepository.delete(existingCat);

    }

    @Transactional
    public void addFriend(Long catId, Long friendId) {
        Cat cat = findCatById(catId);
        Cat friend = findCatById(friendId);
        if (cat.getFriends().contains(friend)) {
            throw new IllegalArgumentException("Cat is already friends with this cat");
        }
        cat.getFriends().add(friend);
        friend.getFriends().add(cat);
    }

    @Transactional
    public void deleteFriend(Long catId, Long friendId) {
        Cat cat = findCatById(catId);
        Cat friend = findCatById(friendId);
        if (!cat.getFriends().contains(friend)) {
            throw new IllegalArgumentException("Cat is not friends with this cat");
        }
        cat.getFriends().remove(friend);
        friend.getFriends().remove(cat);
    }
}