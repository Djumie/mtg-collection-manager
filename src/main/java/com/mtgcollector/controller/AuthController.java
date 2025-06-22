package com.mtgcollector.controller;

import com.mtgcollector.dto.UserDto;
import com.mtgcollector.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserDto.AuthResponseDto> register(@Valid @RequestBody UserDto.CreateUserDto createUserDto) {
        UserDto.AuthResponseDto response = authService.register(createUserDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto.AuthResponseDto> login(@Valid @RequestBody UserDto.LoginDto loginDto) {
        UserDto.AuthResponseDto response = authService.login(loginDto);
        return ResponseEntity.ok(response);
    }

    // Single Constructor
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
}
