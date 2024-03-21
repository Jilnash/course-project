package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.response.AppException;
import com.jilnash.courseproject.dto.request.auth.LoginFormDTO;
import com.jilnash.courseproject.dto.request.auth.RegisterFormDTO;
import com.jilnash.courseproject.model.participants.User;
import com.jilnash.courseproject.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    @Autowired
    private final UserService userService;

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final JwtUtils jwtUtils;

    public ResponseEntity<?> createToken(LoginFormDTO form) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword())
            );
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(
                    new AppException("Incorrect username or password", HttpStatus.UNAUTHORIZED.value()),
                    HttpStatus.UNAUTHORIZED
            );
        }

        UserDetails user = userService.loadUserByUsername(form.getUsername());

        return new ResponseEntity<>(jwtUtils.generateToken(user), HttpStatus.OK);
    }

    public ResponseEntity<?> createUser(RegisterFormDTO form) {

        if (!form.getPassword().equals(form.getConfirmPassword())) {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }

        if (userService.exists(form.getLogin())) {
            return new ResponseEntity<>(
                    new AppException("User with this login already exists", HttpStatus.BAD_REQUEST.value()),
                    HttpStatus.BAD_REQUEST
            );
        }

        User user = userService.createUser(form);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
