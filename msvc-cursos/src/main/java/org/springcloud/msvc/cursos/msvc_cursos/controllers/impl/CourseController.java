package org.springcloud.msvc.cursos.msvc_cursos.controllers.impl;

import feign.FeignException;
import org.springcloud.msvc.cursos.msvc_cursos.commons.models.User;
import org.springcloud.msvc.cursos.msvc_cursos.commons.models.entities.Course;
import org.springcloud.msvc.cursos.msvc_cursos.controllers.CourseApi;
import org.springcloud.msvc.cursos.msvc_cursos.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class CourseController implements CourseApi {

    @Autowired
    private CourseService courseService;

    @Override
    public ResponseEntity<List<Course>> list() {
        return ResponseEntity.ok(courseService.list());
    }

    @Override
    public ResponseEntity<?> detail(Long id) {
        Optional<Course> o = courseService.byId(id);
        if (o.isPresent()) {
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> create(Course course, BindingResult result) {
        if (result.hasErrors()) {
            return validate(result);
        }
        Course courseDb = courseService.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDb);
    }

    @Override
    public ResponseEntity<?> edit(Course course, BindingResult result, Long id) {

        if (result.hasErrors()) {
            return validate(result);
        }

        Optional<Course> o = courseService.byId(id);
        if (o.isPresent()) {
            Course courseDb = o.get();
            courseDb.setName(course.getName());
            return ResponseEntity.status(HttpStatus.CREATED).body(courseService.save(courseDb));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Optional<Course> o = courseService.byId(id);
        if (o.isPresent()) {
            courseService.delete(o.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> assignUser(User user, Long courseId) {
        Optional<User> o;
        try {
            o = courseService.assignUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "User by id don't exist " +
                            "or error in comunication: " + e.getMessage()));
        }
        if(o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> createUser(User user, Long courseId) {
        Optional<User> o;
        try {
            o = courseService.createUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "User hasn't created " +
                            "or error in comunication: " + e.getMessage()));
        }
        if(o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> deleteUser(User user, Long courseId) {
        Optional<User> o;
        try {
            o = courseService.removeUser(user, courseId);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("message", "User by id don't exist " +
                            "or error in comunication: " + e.getMessage()));
        }
        if(o.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }

}