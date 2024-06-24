package com.gmx.newCatDeadInsideProject.securityModule.controllers;

import com.gmx.newCatDeadInsideProject.repositoryModule.users.entities.UserAcc;
import com.gmx.newCatDeadInsideProject.repositoryModule.users.models.Role;
import com.gmx.newCatDeadInsideProject.repositoryModule.users.models.RoleNames;
import com.gmx.newCatDeadInsideProject.securityModule.dto.CreateUserDto;
import com.gmx.newCatDeadInsideProject.securityModule.dto.UserDto;
import com.gmx.newCatDeadInsideProject.securityModule.dto.UserRegistrationResponseDto;
import com.gmx.newCatDeadInsideProject.securityModule.exceptions.UnauthException;
import com.gmx.newCatDeadInsideProject.securityModule.exceptions.UserExistingException;
import com.gmx.newCatDeadInsideProject.securityModule.utils.UserUtil;
import com.gmx.newCatDeadInsideProject.securityModule.services.AdminService;
import com.gmx.newCatDeadInsideProject.securityModule.services.JwtTokenService;
import com.gmx.newCatDeadInsideProject.securityModule.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admins")
public class AdminController {
    private final AdminService adminService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final JwtTokenService jwtTokenService;

    @Autowired
    public AdminController(AdminService adminService, BCryptPasswordEncoder bCryptPasswordEncoder,
                           UserService userService, JwtTokenService jwtTokenService) {
        this.adminService = adminService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.jwtTokenService = jwtTokenService;
    }

    @GetMapping
    public List<UserDto> getAllAdmins() {
        return adminService.getAllAdmins().stream()
                .map(admin -> new UserDto(admin.getUser_id(), admin.getUsername(),
                        admin.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet())))
                .toList();
    }

    @PostMapping("/registerAdmin")
    public UserRegistrationResponseDto createAdmin(@RequestBody CreateUserDto userDto) throws UserExistingException {
        UserAcc userAcc = new UserAcc(userDto.username(), bCryptPasswordEncoder.encode(userDto.password()));
        adminService.createAdmin(userAcc);

        return new UserRegistrationResponseDto(userAcc);
    }

    @PostMapping("/registerUser")
    public UserRegistrationResponseDto createUser(@RequestBody CreateUserDto userDto,
                                                  @RequestHeader(name = "Authorization") String authorization)
            throws UserExistingException {
        String token = authorization.substring(7);
        UserAcc admin = userService.findByUsername(jwtTokenService.getUsernameFromToken(token)).orElseThrow();

        if (!UserUtil.hasPermission(admin, RoleNames.ADMIN.getName()))
            throw new UnauthException();

        UserAcc userAcc = new UserAcc(userDto.username(), bCryptPasswordEncoder.encode(userDto.password()));
        userService.createNewUser(userAcc);

        return new UserRegistrationResponseDto(userAcc);
    }

    @DeleteMapping("/removeAdmin")
    public void deleteAdmin(@RequestParam Long id, @RequestHeader(name = "Authorization") String authorization) {
        String token = authorization.substring(7);
        UserAcc admin = userService.findByUsername(jwtTokenService.getUsernameFromToken(token)).orElseThrow();

        if (!UserUtil.hasPermission(admin, RoleNames.ADMIN.getName()))
            throw new UnauthException();

        if (!id.equals(admin.getUser_id()))
            throw new UnauthException();

        adminService.removeAdmin(admin);

    }

    @DeleteMapping("/removeUser")
    public void deleteUser(@RequestParam Long id, @RequestHeader(name = "Authorization") String authorization) {
        String token = authorization.substring(7);
        UserAcc admin = userService.findByUsername(jwtTokenService.getUsernameFromToken(token)).orElseThrow();

        if (!UserUtil.hasPermission(admin, RoleNames.ADMIN.getName()))
            throw new UnauthException();

        UserAcc userAcc = userService.findById(id);

        if (UserUtil.hasPermission(userAcc, RoleNames.ADMIN.getName()))
            throw new IllegalArgumentException("You are not allowed to delete admin");

        userService.deleteUser(userAcc);
    }
}
