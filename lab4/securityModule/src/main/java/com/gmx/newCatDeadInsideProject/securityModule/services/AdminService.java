package com.gmx.newCatDeadInsideProject.securityModule.services;


import com.gmx.newCatDeadInsideProject.repositoryModule.users.entities.UserAcc;
import com.gmx.newCatDeadInsideProject.repositoryModule.users.models.Role;
import com.gmx.newCatDeadInsideProject.repositoryModule.users.repositories.RoleRepository;
import com.gmx.newCatDeadInsideProject.repositoryModule.users.repositories.UserRepository;
import com.gmx.newCatDeadInsideProject.securityModule.exceptions.UserExistingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public List<UserAcc> getAllAdmins() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles()
                        .stream()
                        .map(Role::getName)
                        .toList()
                        .contains("ROLE_ADMIN"))
                .toList();
    }

    public void createAdmin(UserAcc userAcc) throws UserExistingException {
        if (userRepository.findByUsername(userAcc.getUsername()).isPresent())
            throw new UserExistingException("Admin with name: " + userAcc.getUsername() + " already exists");

        userAcc.setRoles(List.of(roleRepository.findByName("ROLE_ADMIN").orElseThrow()));
        System.out.println("Admin created: " + userAcc.getUsername());
        userRepository.save(userAcc);
    }

    public void removeAdmin(UserAcc userAcc) throws UsernameNotFoundException {
        if (userRepository.findByUsername(userAcc.getUsername()).isEmpty())
            throw new UsernameNotFoundException("Admin with name: " + userAcc.getUsername() + " does not exist");

        if (userRepository.findByUsername(userAcc.getUsername()).isPresent() &&
                !userAcc.getRoles().stream().map(Role::getName).toList().contains("ROLE_ADMIN"))
            throw new IllegalStateException("UserAcc " + userAcc.getUsername() + " is not an admin");

        userRepository.delete(userAcc);
    }
}
