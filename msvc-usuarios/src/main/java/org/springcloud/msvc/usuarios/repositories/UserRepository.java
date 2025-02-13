package org.springcloud.msvc.usuarios.repositories;

import org.springcloud.msvc.usuarios.commons.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByEmail(String email);
}