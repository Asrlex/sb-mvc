package dev.api.springmvc.api.users;

import dev.api.springmvc.common.audit.SoftDeleteRepository;
import dev.api.springmvc.common.entities.models.User;

import java.util.Optional;

public interface UserRepository extends SoftDeleteRepository<User, Integer> {
	Optional<User> findByEmail(String email);
	Optional<User> findByUsername(String username);
}
