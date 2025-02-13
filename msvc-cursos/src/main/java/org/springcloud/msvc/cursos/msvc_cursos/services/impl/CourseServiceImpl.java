package org.springcloud.msvc.cursos.msvc_cursos.services.impl;

import org.springcloud.msvc.cursos.msvc_cursos.clients.UserClientRest;
import org.springcloud.msvc.cursos.msvc_cursos.commons.models.User;
import org.springcloud.msvc.cursos.msvc_cursos.commons.models.entities.Course;
import org.springcloud.msvc.cursos.msvc_cursos.commons.models.entities.CourseUser;
import org.springcloud.msvc.cursos.msvc_cursos.repositories.CourseRepository;
import org.springcloud.msvc.cursos.msvc_cursos.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepository repository;

    @Autowired
    private UserClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Course> list() {
        return (List<Course>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Course> byId(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional
    public Course save(Course course) {
        return repository.save(course);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<User> assignUser(User user, Long courseId) {
        Optional<Course> o = repository.findById(courseId);
        if (o.isPresent()) {
            User userMsvc = client.detail(user.getId());

            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());

            course.addCourseUser(courseUser);
            repository.save(course);
            return Optional.of(userMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> createUser(User user, Long courseId) {
        Optional<Course> o = repository.findById(courseId);
        if (o.isPresent()) {
            User userNewMsvc = client.create(user);

            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userNewMsvc.getId());

            course.addCourseUser(courseUser);
            repository.save(course);
            return Optional.of(userNewMsvc);
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<User> removeUser(User user, Long courseId) {
        Optional<Course> o = repository.findById(courseId);
        if (o.isPresent()) {
            User userMsvc = client.detail(user.getId());

            Course course = o.get();
            CourseUser courseUser = new CourseUser();
            courseUser.setUserId(userMsvc.getId());

            course.removeCourseUser(courseUser);
            repository.save(course);
            return Optional.of(userMsvc);
        }

        return Optional.empty();
    }
}
