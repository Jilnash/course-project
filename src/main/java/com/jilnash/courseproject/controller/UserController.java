package com.jilnash.courseproject.controller;

import com.jilnash.courseproject.dto.request.auth.PasswordChangeDTO;
import com.jilnash.courseproject.dto.request.participants.UserDTO;
import com.jilnash.courseproject.dto.response.AppException;
import com.jilnash.courseproject.dto.response.AppResponse;
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
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(
                new AppResponse(
                        "List of users",
                        200,
                        userService.getUsers()
                )
        );
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {

        return ResponseEntity.ok(
                new AppResponse(
                        "User found successfully",
                        200,
                        userService.getUserById(id)
                )
        );
    }

    @PatchMapping("{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id,
                                        @Valid @RequestBody UserDTO userDTO) {

        return ResponseEntity.ok(
                new AppResponse(
                        "User updated successfully",
                        200,
                        userService.updateUser(id, userDTO)
                )
        );
    }

    @PostMapping("{id}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long id,
                                            @Valid @RequestBody PasswordChangeDTO passwordChangeDTO) {

        if (!passwordChangeDTO.getNewPassword().equals(passwordChangeDTO.getConfirmNewPassword()))
            return ResponseEntity.badRequest().body(
                    new AppException("Passwords do not match", 400)
            );

        return ResponseEntity.ok(
                new AppResponse(
                        "Password changed successfully",
                        200,
                        userService.changePassword(id, passwordChangeDTO)
                )
        );
    }
}
