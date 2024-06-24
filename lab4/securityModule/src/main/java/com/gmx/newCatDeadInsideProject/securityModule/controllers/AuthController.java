package com.gmx.newCatDeadInsideProject.securityModule.controllers;
import com.gmx.newCatDeadInsideProject.securityModule.dto.JwtRequestDto;
import com.gmx.newCatDeadInsideProject.securityModule.dto.JwtResponseDto;
import com.gmx.newCatDeadInsideProject.securityModule.exceptions.AuthErrorException;
import com.gmx.newCatDeadInsideProject.securityModule.services.JwtTokenService;
import com.gmx.newCatDeadInsideProject.securityModule.services.UserService;
import com.gmx.newCatDeadInsideProject.securityModule.utils.CustomAuthenticationProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationManager manager;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @PostMapping("/api/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequestDto authRequestDto) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.username(),
                    authRequestDto.password()));
            System.out.println(authRequestDto.username());
            System.out.println(authRequestDto.password());
        } catch (BadCredentialsException e) {
            System.out.println("хубабуба");
            return new ResponseEntity<>(new AuthErrorException(HttpStatus.UNAUTHORIZED.value(),
                    "incorrect login or password"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequestDto.username());
        String token = jwtTokenService.generateToken(userDetails);
        JwtResponseDto body = new JwtResponseDto(token);
        return ResponseEntity.ok(body);
    }
}
