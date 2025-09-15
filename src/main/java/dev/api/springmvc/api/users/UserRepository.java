package dev.api.springmvc.api.users;

import dev.api.springmvc.common.audit.AuditRepository;
import dev.api.springmvc.common.entities.models.Users;

import java.util.Optional;

public interface UserRepository extends AuditRepository<Users, Long> {
	Optional<Users> findByEmail(String email);
	Optional<Users> findByUsername(String username);
}
