package com.mtgcollector.service;

import com.mtgcollector.dto.UserDto;
import com.mtgcollector.entity.User;
import com.mtgcollector.exception.DuplicateUsernameException;
import com.mtgcollector.exception.UserNotFoundException;
import com.mtgcollector.repository.UserRepository;
import com.mtgcollector.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    public UserDto.AuthResponseDto register(UserDto.CreateUserDto createUserDto) {
        if (userRepository.existsByUsername(createUserDto.getUsername())) {
            throw new DuplicateUsernameException("Username already exists");
        }

        if (userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new DuplicateUsernameException("Email already exists");
        }

        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(createUserDto.getPassword()));
        user.setEmail(createUserDto.getEmail());

        user = userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername());

        return new UserDto.AuthResponseDto(token, convertToUserDto(user), "Bearer", jwtUtil.getExpirationTime());
    }

    public UserDto.AuthResponseDto login(UserDto.LoginDto loginDto) {
        Authentication authentication;
        authentication = AuthenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));

        String token = jwtUtil.generateToken(authentication.getName());

        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        return new UserDto.AuthResponseDto(token, convertToUserDto(user), "Bearer", jwtUtil.getExpirationTime());
    }

    private UserDto convertToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setCreatedAt(user.getCreatedAt());
        // Set Calculated fields if needed
        return dto;
    }
}