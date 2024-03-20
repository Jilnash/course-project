package com.jilnash.courseproject.service;

import com.jilnash.courseproject.model.education.Aspect;
import com.jilnash.courseproject.repo.education.AspectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AspectService {

    @Autowired
    private AspectRepo aspectRepo;

    public Aspect getAspect(Long id) {
        return aspectRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Aspect " + id + " not found"));
    }
}
