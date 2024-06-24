package com.gmx.newCatDeadInsideProject.repositoryModule.cats.repositories;
import com.gmx.newCatDeadInsideProject.repositoryModule.cats.entities.Cat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {
}
