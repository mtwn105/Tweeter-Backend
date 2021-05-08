package com.tweetapp.app.service.impl;

import com.tweetapp.app.dao.AuthDao;
import com.tweetapp.app.dao.entity.Role;
import com.tweetapp.app.dao.entity.User;
import com.tweetapp.app.model.*;
import com.tweetapp.app.security.JwtUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AuthServiceImplTest {

    private AuthServiceImpl authServiceImplUnderTest;

    @Before
    public void setUp() {
        authServiceImplUnderTest = new AuthServiceImpl();
        authServiceImplUnderTest.authenticationManager = mock(AuthenticationManager.class);
        authServiceImplUnderTest.jwtUtils = mock(JwtUtils.class);
        authServiceImplUnderTest.encoder = mock(PasswordEncoder.class);
        authServiceImplUnderTest.authDao = mock(AuthDao.class);
    }

    @Test
    public void testSignIn() {
        // Setup
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        final JwtResponse expectedResult = new JwtResponse("token", "id", "username", "email");
        when(authServiceImplUnderTest.authenticationManager.authenticate(null)).thenReturn(null);
        when(authServiceImplUnderTest.jwtUtils.generateJwtToken(null)).thenReturn("result");

        // Run the test
        final JwtResponse result = authServiceImplUnderTest.signIn(loginRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testSignIn_AuthenticationManagerThrowsAuthenticationException() {
        // Setup
        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername("username");
        loginRequest.setPassword("password");

        final JwtResponse expectedResult = new JwtResponse("token", "id", "username", "email");
        when(authServiceImplUnderTest.authenticationManager.authenticate(null)).thenThrow(AuthenticationException.class);
        when(authServiceImplUnderTest.jwtUtils.generateJwtToken(null)).thenReturn("result");

        // Run the test
        final JwtResponse result = authServiceImplUnderTest.signIn(loginRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testSignUp() {
        // Setup
        final SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("username");
        signUpRequest.setEmail("email");
        signUpRequest.setFirstName("firstName");
        signUpRequest.setLastName("lastName");
        signUpRequest.setContactNumber("contactNumber");
        signUpRequest.setPassword("password");

        final MessageResponse expectedResult = new MessageResponse("message", HttpStatus.OK);
        when(authServiceImplUnderTest.authDao.userExistsByUsername("username")).thenReturn(false);
        when(authServiceImplUnderTest.authDao.userExistsByEmail("email")).thenReturn(false);
        when(authServiceImplUnderTest.encoder.encode("charSequence")).thenReturn("result");
        when(authServiceImplUnderTest.authDao.findRoleByName(ERole.ROLE_USER)).thenReturn(Optional.of(new Role(ERole.ROLE_USER)));

        // Configure AuthDao.saveUser(...).
        final User user = new User("username", "firstName", "lastName", "contactNumber", "email", "password");
        when(authServiceImplUnderTest.authDao.saveUser(new User("username", "firstName", "lastName", "contactNumber", "email", "password"))).thenReturn(user);

        // Run the test
        final MessageResponse result = authServiceImplUnderTest.signUp(signUpRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    public void testSignUp_AuthDaoFindRoleByNameReturnsAbsent() {
        // Setup
        final SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setUsername("username");
        signUpRequest.setEmail("email");
        signUpRequest.setFirstName("firstName");
        signUpRequest.setLastName("lastName");
        signUpRequest.setContactNumber("contactNumber");
        signUpRequest.setPassword("password");

        final MessageResponse expectedResult = new MessageResponse("message", HttpStatus.OK);
        when(authServiceImplUnderTest.authDao.userExistsByUsername("username")).thenReturn(false);
        when(authServiceImplUnderTest.authDao.userExistsByEmail("email")).thenReturn(false);
        when(authServiceImplUnderTest.encoder.encode("charSequence")).thenReturn("result");
        when(authServiceImplUnderTest.authDao.findRoleByName(ERole.ROLE_USER)).thenReturn(Optional.empty());

        // Configure AuthDao.saveUser(...).
        final User user = new User("username", "firstName", "lastName", "contactNumber", "email", "password");
        when(authServiceImplUnderTest.authDao.saveUser(new User("username", "firstName", "lastName", "contactNumber", "email", "password"))).thenReturn(user);

        // Run the test
        final MessageResponse result = authServiceImplUnderTest.signUp(signUpRequest);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }
}
