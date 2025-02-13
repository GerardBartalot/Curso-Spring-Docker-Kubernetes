package org.springcloud.msvc.usuarios.controllers;

import jakarta.validation.Valid;
import org.springcloud.msvc.usuarios.commons.constants.ApiPathVariables;
import org.springcloud.msvc.usuarios.commons.entities.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(ApiPathVariables.API_ROUTE + ApiPathVariables.USER_ROUTE)
public interface UserApi {

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers();

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(
            @PathVariable Long id);

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody User user, BindingResult result);

    @PutMapping("/{id}")
    public ResponseEntity<?> edit(
            @Valid @RequestBody User user, BindingResult result, @PathVariable Long id);

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @PathVariable Long id);

}
