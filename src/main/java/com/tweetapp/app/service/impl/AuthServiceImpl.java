package com.tweetapp.app.service.impl;

import com.tweetapp.app.dao.AuthDao;
import com.tweetapp.app.dao.entity.Role;
import com.tweetapp.app.dao.entity.User;
import com.tweetapp.app.model.*;
import com.tweetapp.app.security.JwtUtils;
import com.tweetapp.app.security.UserDetailsImpl;
import com.tweetapp.app.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AuthDao authDao;

    @Override
    public JwtResponse signIn(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail());
    }

    @Override
    public MessageResponse signUp(SignUpRequest signUpRequest) {

        if (authDao.userExistsByUsername(signUpRequest.getUsername())) {
            return new MessageResponse("Error: Username is already taken!", HttpStatus.BAD_REQUEST);
        }

        if (authDao.userExistsByEmail(signUpRequest.getEmail())) {
            return new MessageResponse("Error: Email is already in use!",HttpStatus.BAD_REQUEST);
        }

        // Create new user's account
        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getFirstName(),
                signUpRequest.getLastName(),
                signUpRequest.getContactNumber(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();

        Role userRole = authDao.findRoleByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);

        user.setRoles(roles);

        authDao.saveUser(user);

        return new MessageResponse("User registered successfully!", HttpStatus.CREATED);
    }
}
