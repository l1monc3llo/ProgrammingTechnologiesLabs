package com.gmx.newCatDeadInsideProject.securityModule.dto;


import java.util.Set;

public record UserDto(Long id, String username, Set<String> roles) {
}
