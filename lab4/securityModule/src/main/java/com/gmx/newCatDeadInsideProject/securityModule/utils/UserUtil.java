package com.gmx.newCatDeadInsideProject.securityModule.utils;


import com.gmx.newCatDeadInsideProject.repositoryModule.users.entities.UserAcc;
import com.gmx.newCatDeadInsideProject.repositoryModule.users.models.Role;

public class UserUtil {
    public static boolean hasPermission(UserAcc userAcc, String permission) {
        return userAcc.getRoles().stream()
                .map(Role::getName)
                .anyMatch(roleName -> roleName.equals(permission));
    }
}
