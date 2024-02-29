package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.dto.request.auth.PasswordChangeDTO;
import com.jilnash.courseproject.dto.request.participants.UserDTO;
import com.jilnash.courseproject.dto.response.AppException;
import com.jilnash.courseproject.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public String getUsers() {
        return "Users";
    }

    @GetMapping("{id}")
    public String getUser(@PathVariable Long id) {
        return "User: " + id;
    }

    @PatchMapping("{id}")
    public String updateUser(@PathVariable Long id,
                             @Valid @RequestBody UserDTO userDTO) {
        return "User: " + id;
    }
    @PostMapping("{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long id,
                                            @Valid @RequestBody PasswordChangeDTO passwordChangeDTO) {

        if (!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getConfirmNewPassword()))
            return ResponseEntity.badRequest().body(
                    new AppException("Passwords do not match", 400)
            );

        userService.changePassword(id, passwordChangeDTO);

        return ResponseEntity.ok("Password changed successfully");
    }
}
