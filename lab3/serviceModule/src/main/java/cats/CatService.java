package cats;

import cats.repositories.CatRepository;
import cats.entities.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;


@Service
public class CatService {
    @Autowired
    private CatRepository catRepository;

    public void saveCat(Cat cat){
        catRepository.save(cat);
    }

    public Cat findCatById(long id) throws NoSuchElementException {
        return catRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Cat not found"));
    }

    public List<Cat> findAllCats(){
        return catRepository.findAll();
    }

    public void deleteCat(long id){
        Cat existingCat = findCatById(id);
        catRepository.delete(existingCat);

    }

    public void addFriend(long catId, long friendId) {
        Cat cat = findCatById(catId);
        Cat friend = findCatById(friendId);
        if (cat.getFriends().contains(friend)) {
            throw new IllegalArgumentException("Cat is already friends with this cat");
        }
        cat.getFriends().add(friend);
        friend.getFriends().add(cat);
        catRepository.save(cat);
        catRepository.save(friend);
    }

    public void deleteFriend(long catId, long friendId) {
        Cat cat = findCatById(catId);
        Cat friend = findCatById(friendId);
        if (!cat.getFriends().contains(friend)) {
            throw new IllegalArgumentException("Cat is not friends with this cat");
        }
        cat.getFriends().remove(friend);
        friend.getFriends().remove(cat);
        catRepository.save(cat);
        catRepository.save(friend);
    }
}