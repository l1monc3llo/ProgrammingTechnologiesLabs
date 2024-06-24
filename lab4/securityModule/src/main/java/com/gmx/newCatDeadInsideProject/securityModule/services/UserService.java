package com.gmx.newCatDeadInsideProject.securityModule.services;
import com.gmx.newCatDeadInsideProject.repositoryModule.users.entities.UserAcc;
import com.gmx.newCatDeadInsideProject.repositoryModule.users.models.Role;
import com.gmx.newCatDeadInsideProject.repositoryModule.users.repositories.RoleRepository;
import com.gmx.newCatDeadInsideProject.repositoryModule.users.repositories.UserRepository;
import com.gmx.newCatDeadInsideProject.securityModule.exceptions.UserExistingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserAcc findById(Long id) {
        return userRepository.findById(id).orElseThrow();
    }

    public List<UserAcc> findAll() {
        return userRepository.findAll().stream()
                .filter(user -> user.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet())
                        .contains("ROLE_USER"))
                .collect(Collectors.toList());
    }

    public Optional<UserAcc> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAcc userAcc = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(
                userAcc.getUsername(),
                userAcc.getPassword(),
                userAcc.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }

    public void createNewUser(UserAcc userAcc) throws UserExistingException {
        if (userRepository.findByUsername(userAcc.getUsername()).isPresent())
            throw new UserExistingException(String.format("UserAcc with name '%s' already exists", userAcc.getUsername()));

        userAcc.setRoles(List.of(roleRepository.findByName("ROLE_USER").get()));
        System.out.println("UserAcc created: " + userAcc.getUsername());
        userRepository.save(userAcc);
    }

    public void deleteUser(UserAcc userAcc) {
        if (!userRepository.existsById(userAcc.getUser_id()))
            throw new UsernameNotFoundException(String.format("User with '%s' id doesn't exist", userAcc.getUser_id()));

        if (userRepository.findByUsername(userAcc.getUsername()).isPresent()
                && userAcc.getRoles().stream().map(Role::getName).toList().contains("ROLE_ADMIN"))
            throw new IllegalStateException(String.format("User with '%s' is admin", userAcc.getUser_id()));

        userRepository.delete(userAcc);
    }
}
