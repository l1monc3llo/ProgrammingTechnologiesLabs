package com.gmx.newCatDeadInsideProject.securityModule.controllers;

import com.gmx.newCatDeadInsideProject.repositoryModule.users.entities.UserAcc;
import com.gmx.newCatDeadInsideProject.repositoryModule.users.models.Role;
import com.gmx.newCatDeadInsideProject.securityModule.dto.CreateUserDto;
import com.gmx.newCatDeadInsideProject.securityModule.dto.UserDto;
import com.gmx.newCatDeadInsideProject.securityModule.dto.UserRegistrationResponseDto;
import com.gmx.newCatDeadInsideProject.securityModule.exceptions.UnauthException;
import com.gmx.newCatDeadInsideProject.securityModule.exceptions.UserExistingException;
import com.gmx.newCatDeadInsideProject.securityModule.services.JwtTokenService;
import com.gmx.newCatDeadInsideProject.securityModule.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserController(UserService userService, JwtTokenService jwtTokenService,
                          BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAll().stream()
                .map(user -> new UserDto(user.getUser_id(), user.getUsername(),
                        user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet())))
                .toList();
    }

    @PostMapping("/registerUser")
    public UserRegistrationResponseDto createUser(@RequestBody CreateUserDto userDto) throws UserExistingException {
        UserAcc userAcc = new UserAcc(userDto.username(), bCryptPasswordEncoder.encode(userDto.password()));
        userService.createNewUser(userAcc);

        return new UserRegistrationResponseDto(userAcc);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id, @RequestHeader(name = "Authorization") String authorization)
            throws UnauthException {
        String token = authorization.split(" ")[1];
        UserAcc userAcc = userService.findByUsername(jwtTokenService.getUsernameFromToken(token)).orElseThrow();

        if (!id.equals(userAcc.getUser_id()) && !userAcc.getRoles().stream().map(Role::getName).toList().contains("ROLE_ADMIN")) {
            throw new UnauthException();
        }

        userService.deleteUser(userAcc);
    }

}
