package cats;

import cats.dao.CatDao;
import cats.entities.Cat;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
public class CatService {
    private final CatDao catDao;

    public void saveCat(Cat cat){
        catDao.saveCat(cat);
    }

    public Cat findCatById(long id) throws NoSuchElementException {
        return Optional.ofNullable(catDao.findCatById(id))
                .orElseThrow(() -> new NoSuchElementException("Cat not found"));
    }

    public List<Cat> getAllCats(){
        return catDao.getAllCats();
    }

    public void deleteCat(long id){
        Cat existingCat = findCatById(id);
        catDao.deleteCat(existingCat.getId());

    }

    public void addFriend(long catId, long friendId) {
        Cat cat = findCatById(catId);
        Cat friend = findCatById(friendId);
        if (cat.getFriends().contains(friend)) {
            throw new IllegalArgumentException("Cat is already friends with this cat");
        }
        cat.getFriends().add(friend);
        friend.getFriends().add(cat);
        catDao.saveCat(cat);
        catDao.saveCat(friend);
    }

    public void deleteFriend(long catId, long friendId) {
        Cat cat = findCatById(catId);
        Cat friend = findCatById(friendId);
        if (!cat.getFriends().contains(friend)) {
            throw new IllegalArgumentException("Cat is not friends with this cat");
        }
        cat.getFriends().remove(friend);
        friend.getFriends().remove(cat);
        catDao.saveCat(cat);
        catDao.saveCat(friend);
    }
}

