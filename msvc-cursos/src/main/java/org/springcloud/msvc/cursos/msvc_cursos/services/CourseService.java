package org.springcloud.msvc.cursos.msvc_cursos.services;

import org.springcloud.msvc.cursos.msvc_cursos.commons.models.User;
import org.springcloud.msvc.cursos.msvc_cursos.commons.models.entities.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {
    List<Course> list();
    Optional<Course> byId(Long id);
    Course save(Course course);
    void delete(Long id);

    Optional<User> assignUser(User user, Long courseId);
    Optional<User> createUser(User user, Long courseId);
    Optional<User> removeUser(User user, Long courseId);

}
