package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.auth.RegisterFormDTO;
import com.jilnash.courseproject.model.participants.User;
import com.jilnash.courseproject.repo.RoleRepo;
import com.jilnash.courseproject.repo.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    public Optional<User> findByLogin(String login) {
        return userRepo.findByLogin(login);
    }

    public User createUser(RegisterFormDTO form) {

        User user = new User();

        user.setLogin(form.getLogin());
        user.setPassword(encoder.encode(form.getPassword()));
        user.setEmail(form.getEmail());
        user.setPhone(form.getPhone());
        user.setRoles(List.of(roleRepo.findByName("STUDENT")));

        return userRepo.save(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo.findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
