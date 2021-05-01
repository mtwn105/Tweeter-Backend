package com.tweetapp.app.service;

import com.tweetapp.app.model.JwtResponse;
import com.tweetapp.app.model.LoginRequest;
import com.tweetapp.app.model.MessageResponse;
import com.tweetapp.app.model.SignUpRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    JwtResponse signIn(LoginRequest loginRequest);

    MessageResponse signUp(SignUpRequest signUpRequest);
}
