package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.education.AspectDTO;
import com.jilnash.courseproject.model.education.Aspect;
import com.jilnash.courseproject.model.education.Course;
import com.jilnash.courseproject.repo.education.AspectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
public class AspectService {

    @Autowired
    private AspectRepo aspectRepo;

    public Aspect getAspect(Long id) {
        return aspectRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Aspect " + id + " not found"));
    }

    public Aspect createAspect(AspectDTO aspectDTO, Course course) {
        Aspect aspect = new Aspect();
        aspect.setName(aspectDTO.getName());
        aspect.setCourse(course);
        return aspectRepo.save(aspect);
    }

    public List<Aspect> createAspects(List<AspectDTO> aspectDTOS, Course course) {

        List<Aspect> aspects = new LinkedList<>();

        for (AspectDTO aspectDTO : aspectDTOS) {
            Aspect aspect = new Aspect();
            aspect.setName(aspectDTO.getName());
            aspect.setCourse(course);
            aspects.add(aspectRepo.save(aspect));
        }

        return aspects;
    }

    public void updateAspects(List<AspectDTO> aspectDTOS, Course course) {

        for (AspectDTO aspectDTO : aspectDTOS) {
            if (aspectDTO.getId() == null) {
                createAspect(aspectDTO, course);
            } else {
                Aspect aspect = getAspect(aspectDTO.getId());
                aspect.setName(aspectDTO.getName());
                aspect.setCourse(course);
                aspectRepo.save(aspect);
            }
        }
    }
}
