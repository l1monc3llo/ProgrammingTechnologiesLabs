package cats.dao;

import cats.entities.Cat;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
public class CatDao {
    private final EntityManagerFactory entityManagerFactory;
    public void saveCat(Cat cat) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(cat);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }

    public Cat findCatById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Cat foundCat;
        try {
            foundCat = entityManager.find(Cat.class, id);

        } finally {
            entityManager.close();
        }
        return foundCat;
    }

    public List<Cat> getAllCats() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Cat> cats;
        try {
            TypedQuery<Cat> query = entityManager.createQuery("select c from Cat c", Cat.class);
            cats = query.getResultList();
        } finally {
            entityManager.close();
        }
        return cats;
    }

    public void deleteCat(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Cat cat = entityManager.find(Cat.class, id);
            if (cat != null) {
                entityManager.remove(cat);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            entityManager.close();
        }
    }
}
