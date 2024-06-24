package com.gmx.newCatDeadInsideProject.repositoryModule.owners.repositories;
import com.gmx.newCatDeadInsideProject.repositoryModule.owners.entities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
