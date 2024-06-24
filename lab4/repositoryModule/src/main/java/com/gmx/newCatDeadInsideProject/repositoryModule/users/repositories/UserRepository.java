package com.gmx.newCatDeadInsideProject.repositoryModule.users.repositories;


import com.gmx.newCatDeadInsideProject.repositoryModule.users.entities.UserAcc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserAcc, Long> {
    Optional<UserAcc> findByUsername(String username);
}

