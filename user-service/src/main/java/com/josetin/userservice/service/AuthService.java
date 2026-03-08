package com.josetin.userservice.service;

import com.josetin.userservice.dto.request.LoginRequest;
import com.josetin.userservice.dto.request.RegisterRequest;
import com.josetin.userservice.entity.User;
import com.josetin.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public String register(RegisterRequest request){

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(request.role())
                .build();

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(LoginRequest request){

        User user = userRepository.findByUsername(request.username())
                .orElseThrow(()-> new RuntimeException("User Not Found"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())){
            throw new RuntimeException("Invalid password");
        }
        return "Token "+jwtService.generateToken(user.getUsername(), user.getRole());
    }
}
