package org.springcloud.msvc.cursos.msvc_cursos.controllers;

import jakarta.validation.Valid;
import org.springcloud.msvc.cursos.msvc_cursos.commons.constants.ApiPathVariables;
import org.springcloud.msvc.cursos.msvc_cursos.commons.models.User;
import org.springcloud.msvc.cursos.msvc_cursos.commons.models.entities.Course;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(ApiPathVariables.API_ROUTE + ApiPathVariables.COURSE_ROUTE)
public interface CourseApi {

    @GetMapping
    public ResponseEntity<List<Course>> list();

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(
            @PathVariable Long id);

    @PostMapping("/")
    public ResponseEntity<?> create(
            @Valid @RequestBody Course course, BindingResult result);

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(
            @Valid @RequestBody Course course, BindingResult result, @PathVariable Long id);

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id);

    @PutMapping("/assign-user/{courseId}")
    public ResponseEntity<?> assignUser(
            @RequestBody User user, @PathVariable Long courseId);

    @PostMapping("/create-user/{courseId}")
    public ResponseEntity<?> createUser(
            @RequestBody User user, @PathVariable Long courseId);

    @DeleteMapping("/delete-user/{courseId}")
    public ResponseEntity<?> deleteUser(
            @RequestBody User user, @PathVariable Long courseId);


}