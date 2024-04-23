package owners.dao;

import lombok.RequiredArgsConstructor;
import owners.entities.Owner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

@RequiredArgsConstructor
public class OwnerDao {
    private final EntityManagerFactory entityManagerFactory;

    public void saveOwner(Owner owner) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(owner);
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

    public Owner findOwnerById(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        Owner foundowner;
        try {
            foundowner = entityManager.find(Owner.class, id);

        } finally {
            entityManager.close();
        }
        return foundowner;
    }

    public List<Owner> getAllOwners() {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        List<Owner> owners;
        try {
            TypedQuery<Owner> query = entityManager.createQuery("select o from Owner o", Owner.class);
            owners = query.getResultList();
        } finally {
            entityManager.close();
        }
        return owners;
    }

    public void deleteOwner(long id) {
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Owner owner = entityManager.find(Owner.class, id);
            if (owner != null) {
                entityManager.remove(owner);
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