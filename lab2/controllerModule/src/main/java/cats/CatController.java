package cats;


import cats.entities.Cat;

import java.util.List;

public class CatController {
    private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

    public List<Cat> getAllCats() {
        List<Cat> cats = catService.getAllCats();
        return cats;
    }

    public Cat getCatById(long id) {
        Cat cat = catService.findCatById(id);
        return cat;
    }

    public String addCat(Cat cat) {
        catService.saveCat(cat);
        return "Cat added successfully";
    }

    public String deleteCat(long id) {
        catService.deleteCat(id);
        return "Cat deleted successfully";
    }
}