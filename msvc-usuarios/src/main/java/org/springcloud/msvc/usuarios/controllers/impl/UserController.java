package org.springcloud.msvc.usuarios.controllers.impl;

import jakarta.validation.Valid;
import org.springcloud.msvc.usuarios.commons.entities.User;
import org.springcloud.msvc.usuarios.controllers.UserApi;
import org.springcloud.msvc.usuarios.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class UserController implements UserApi {

    @Autowired
    private UserService service;

    @Override
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(service.list());
    }

    @Override
    public ResponseEntity<?> detail(Long id) {
        Optional<User> userOptional = service.byId(id);
        return userOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> create(User user, BindingResult result) {

        if(service.findByEmail(user.getEmail()).isPresent()){
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("message", "Ya existe un usuario con ese email"));
        }

        if (result.hasErrors()) {
            return validate(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(user));
    }

    @Override
    public ResponseEntity<?> edit(User user, BindingResult result, Long id) {

        if (result.hasErrors()) {
            return validate(result);
        }

        Optional<User> o = service.byId(id);
        if (o.isPresent()) {
            User userDb = o.get();
            if(!user.getEmail().equalsIgnoreCase(userDb.getEmail()) && service.findByEmail(user.getEmail()).isPresent()){
                return ResponseEntity.badRequest()
                        .body(Collections.singletonMap("message", "Ya existe un usuario con ese email"));
            }

            userDb.setName(user.getName());
            userDb.setEmail(user.getEmail());
            userDb.setPassword(user.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(userDb));
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> delete(Long id) {
        Optional<User> o = service.byId(id);
        if (o.isPresent()) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<Map<String, String>> validate(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errors);
    }


}