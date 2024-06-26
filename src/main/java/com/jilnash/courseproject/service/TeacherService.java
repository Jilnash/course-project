package com.jilnash.courseproject.service;

import com.jilnash.courseproject.dto.request.participants.TeacherDTO;
import com.jilnash.courseproject.model.education.Course;
import com.jilnash.courseproject.model.participants.Teacher;
import com.jilnash.courseproject.repo.participants.TeacherRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherService {

    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private UserService userService;

    public List<Teacher> getTeachers() {
        return teacherRepo.findAll();
    }

    public Teacher getTeacher(Long id) {
        return teacherRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Teacher with id: " + id + " not found"));
    }

    public Teacher getTeacher(String login) {
        return teacherRepo
                .findByUserLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Teacher with login: " + login + " not found"));
    }

    public Boolean exists(String login) {
        return teacherRepo.existsByUserLogin(login);
    }

    public Teacher createTeacher(TeacherDTO teacherDTO) {

        Teacher teacher = new Teacher();

        teacher.setName(teacherDTO.getName());
        teacher.setSurname(teacherDTO.getSurname());
        teacher.setDescription(teacherDTO.getDescription());
        teacher.setUser(userService.getUser(teacherDTO.getUserId()));

        if (teacherDTO.getPhoto() != null)
            teacher.setPhoto(teacherDTO.getPhoto());

        if (teacherDTO.getMediaLink() != null)
            teacher.setMediaLink(teacherDTO.getMediaLink());

        return teacherRepo.save(teacher);
    }

    public boolean updateTeacher(Long id, TeacherDTO teacherDTO) {

        Teacher teacher = teacherRepo
                .findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Teacher not found"));

        teacher.setName(teacherDTO.getName());
        teacher.setSurname(teacherDTO.getSurname());
        teacher.setDescription(teacherDTO.getDescription());

        if (teacherDTO.getPhoto() != null)
            teacher.setPhoto(teacherDTO.getPhoto());

        if (teacherDTO.getMediaLink() != null)
            teacher.setMediaLink(teacherDTO.getMediaLink());

        teacherRepo.save(teacher);

        return true;
    }

    public boolean checkTeacherCourseAccess(Course course, Teacher teacher) {

        if (course.getAuthor().getId().equals(teacher.getId()))
            return true;

        return course
                .getTeachers()
                .stream()
                .map(Teacher::getId)
                .anyMatch(id -> id.equals(teacher.getId()));
    }
}
