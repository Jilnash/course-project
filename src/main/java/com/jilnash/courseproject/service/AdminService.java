package com.jilnash.courseproject.service;

import com.jilnash.courseproject.model.participants.Admin;
import com.jilnash.courseproject.repo.participants.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private AdminRepo adminRepo;

    public Admin getAdmin(String login) {
        return adminRepo
                .findByUserLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
    }
}
