package com.tweetapp.app.controller;

import com.tweetapp.app.model.JwtResponse;
import com.tweetapp.app.model.LoginRequest;
import com.tweetapp.app.model.MessageResponse;
import com.tweetapp.app.model.SignUpRequest;
import com.tweetapp.app.security.JwtUtils;
import com.tweetapp.app.security.UserDetailsImpl;
import com.tweetapp.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1.0/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        JwtResponse response = authService.signIn(loginRequest);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {

        MessageResponse response = authService.signUp(signUpRequest);

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

}
