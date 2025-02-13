package org.springcloud.msvc.usuarios.services;

import org.springcloud.msvc.usuarios.commons.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> list();
    Optional<User> byId(Long id);
    User save(User user);
    void delete(Long id);

    Optional<User> findByEmail(String email);
}
