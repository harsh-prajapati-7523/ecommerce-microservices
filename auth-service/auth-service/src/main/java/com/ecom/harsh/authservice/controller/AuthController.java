package com.ecom.harsh.authservice.controller;

import com.ecom.harsh.authservice.model.UserLoginDTO;
import com.ecom.harsh.authservice.model.UserMapper;
import com.ecom.harsh.authservice.model.Users;
import com.ecom.harsh.authservice.model.UsersDTO;
import com.ecom.harsh.authservice.repository.UserRepository;
import com.ecom.harsh.authservice.security.JwtUtil;

import jakarta.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtUtil jwtUtil;
    private UserMapper userMapper ;
    
    public AuthController(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtUtil jwtUtil,UserMapper userMapper){
        this.userRepository =userRepository;
        this.passwordEncoder=passwordEncoder;
        this.jwtUtil=jwtUtil;
        this.userMapper = userMapper;
    }
    
    @PostMapping("/register")
    public String register(@RequestBody @Valid UsersDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(userMapper.toEntity(userDTO));
        return "User registered successfully!";
    }

    @PostMapping("/login")
    public String login(@RequestBody @Valid UserLoginDTO userDTO) {
        Optional<Users> optionalUser = userRepository.findByUsername(userDTO.getUsername());
        if (optionalUser.isPresent() && passwordEncoder.matches(userDTO.getPassword(), optionalUser.get().getPassword())) {
            return jwtUtil.generateToken(userDTO.getUsername(), optionalUser.get().getRole());
        }
        return "Invalid username or password";
    }
}
