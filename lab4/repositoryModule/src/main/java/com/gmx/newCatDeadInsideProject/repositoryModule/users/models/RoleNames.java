package com.gmx.newCatDeadInsideProject.repositoryModule.users.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum RoleNames {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String name;
}
