package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.auth.AuthorityListDTO;
import com.jilnash.courseproject.dto.request.auth.PasswordChangeDTO;
import com.jilnash.courseproject.dto.request.auth.RegisterFormDTO;
import com.jilnash.courseproject.dto.request.participants.UserDTO;
import com.jilnash.courseproject.model.participants.Role;
import com.jilnash.courseproject.model.participants.User;
import com.jilnash.courseproject.repo.participants.RoleRepo;
import com.jilnash.courseproject.repo.participants.UserRepo;
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

    @Autowired
    StudentService studentService;

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

    public User getUserById(Long id) {
        return userRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public List<User> getUsers() {
        return userRepo.findAll();
    }

    public boolean updateUser(Long id, UserDTO userDTO) {

        User user = getUserById(id);

        userRepo.findByLogin(userDTO.getLogin())
                .ifPresent(u -> {
                    if (!u.getId().equals(id))
                        throw new IllegalArgumentException("User with this login already exists");
                });

        userRepo.findByEmail(userDTO.getEmail())
                .ifPresent(u -> {
                    if (!u.getId().equals(id))
                        throw new IllegalArgumentException("User with this email already exists");
                });

        userRepo.findByPhone(userDTO.getPhone())
                .ifPresent(u -> {
                    if (!u.getId().equals(id))
                        throw new IllegalArgumentException("User with this phone already exists");
                });

        user.setLogin(userDTO.getLogin());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());

        return userRepo.save(user) != null;
    }

    public boolean changePassword(Long id, PasswordChangeDTO passwordChangeDTO) {

        User user = userRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (!encoder.matches(passwordChangeDTO.getOldPassword(), user.getPassword()))
            throw new IllegalArgumentException("Old password is incorrect");

        if (encoder.matches(passwordChangeDTO.getNewPassword(), user.getPassword()))
            throw new IllegalArgumentException("New password must be different from the old one");

        user.setPassword(encoder.encode(passwordChangeDTO.getNewPassword()));

        userRepo.save(user);
        return true;
    }

    public boolean changeAuthority(Long id, AuthorityListDTO authorityListDTO) {

        User user = getUserById(id);

        List<Role> newRoles = authorityListDTO
                .getAuthorities().stream()
                .map(roleRepo::findByName)
                .collect(Collectors.toList());

        user.setRoles(newRoles);

        userRepo.save(user);
        return true;
    }

    public boolean hasAccessToCourse(String username, Long courseId) {

        User user = findByLogin(username).get();

        List<String> userRoles = user.getRoles().stream().map(Role::getName).toList();

        if (userRoles.size() == 1 && userRoles.contains("STUDENT"))
            return studentService.hasAccessToCourse(studentService.getStudent(user).getId(), courseId);

        return true;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepo
                .findByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(
                user.getLogin(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }
}
