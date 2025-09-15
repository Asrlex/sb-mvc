package dev.api.springmvc.api.users;

import dev.api.springmvc.api.auth.entities.LoginRequest;
import dev.api.springmvc.api.users.dtos.UpdateUserDto;
import dev.api.springmvc.api.users.dtos.UserDto;
import dev.api.springmvc.common.entities.models.Users;
import dev.api.springmvc.common.entities.search.SearchCriteria;
import dev.api.springmvc.common.entities.search.SqlParameters;
import dev.api.springmvc.common.exceptions.ResourceNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * Gets all users
	 *
	 * @return List<User> - all users
	 */
	public List<UserDto> list(SearchCriteria searchCriteria) {
		return this.userRepository.findAll().stream()
				.filter(user -> {
					if (searchCriteria.getFilters() != null) {
						for (SearchCriteria.Filter filter : searchCriteria.getFilters()) {
							boolean like = Objects.equals(filter.operator().toString(), SqlParameters.SqlOperator.LIKE);
							if (filter.field().equals("username") && like) {
								String value = (String) filter.value();
								if (!user.getUsername().contains(value)) {
									return false;
								}
							}
							if (filter.field().equals("email") && like) {
								String value = (String) filter.value();
								if (!user.getEmail().contains(value)) {
									return false;
								}
							}
						}
					}
					return true;
				})
				.map(Users::generateDto)
				.sorted((u1, u2) -> {
					if (searchCriteria.getSorters() != null) {
						for (SearchCriteria.Sorter sorter : searchCriteria.getSorters()) {
							boolean asc = Objects.equals(sorter.operator().toString(), SqlParameters.SqlOrder.ASC);
							if (sorter.field().equals("username")) {
								if (asc) {
									return u1.getUsername().compareTo(u2.getUsername());
								} else {
									return u2.getUsername().compareTo(u1.getUsername());
								}
							}
							if (sorter.field().equals("email")) {
								if (asc) {
									return u1.getEmail().compareTo(u2.getEmail());
								} else {
									return u2.getEmail().compareTo(u1.getEmail());
								}
							}
						}
					}
					return u1.getId().compareTo(u2.getId());
				})
				.skip(searchCriteria.getPage() != null && searchCriteria.getPageSize() != null
						? (long) (searchCriteria.getPage() - 1) * searchCriteria.getPageSize()
						: 0L)
				.limit(searchCriteria.getPageSize() != null ? searchCriteria.getPageSize() : Long.MAX_VALUE)
				.toList();
	}

	/**
	 * Gets all users
	 *
	 * @return List<User> - all users
	 */
	public List<UserDto> findAll() {
		return this.userRepository.findAll().stream()
				.map(Users::generateDto)
				.sorted(Comparator.comparing(UserDto::getId))
				.toList();
	}

	/**
	 * Gets all users including deleted ones
	 *
	 * @return List<User> - all users including deleted ones
	 */
	public List<UserDto> findAllIncludingDeleted() {
		return this.userRepository.findAllIncludingDeleted().stream()
				.map(Users::generateDto)
				.sorted(Comparator.comparing(UserDto::getId))
				.toList();
	}

	/**
	 * Get the current authenticated user
	 *
	 * @param auth - Authentication object
	 * @return User - current user
	 */
	public UserDto getCurrent(Authentication auth) {
		String email = auth.getName();
		return findByEmail(email);
	}

	/**
	 * Get a user by its ID
	 *
	 * @param id - the user's ID
	 * @return User - requested user
	 */
	public UserDto findById(Long id) {
		Optional<Users> requestedUser = this.userRepository.findById(id);
		if (requestedUser.isPresent()) {
			Users user = requestedUser.get();
			return user.generateDto();
		} else {
			throw new ResourceNotFoundException("User with id " + id + " not found");
		}
	}

	/**
	 * Get a user by its ID including deleted ones
	 *
	 * @param id - the user's ID
	 * @return User - requested user
	 */
	public UserDto findByIdIncludingDeleted(Long id) {
		Optional<Users> requestedUser = this.userRepository.findByIdIncludingDeleted(id).stream().findFirst();
		if (requestedUser.isPresent()) {
			Users user = requestedUser.get();
			return user.generateDto();
		} else {
			throw new ResourceNotFoundException("User with id " + id + " not found");
		}
	}

	/**
	 * Get a user by its email
	 *
	 * @param email - the user's email
	 * @return User - requested user
	 */
	public UserDto findByEmail(String email) {
		Optional<Users> requestedUser = this.userRepository.findByEmail(email);
		if (requestedUser.isPresent()) {
			Users user = requestedUser.get();
			return user.generateDto();
		} else {
			throw new ResourceNotFoundException("User with email " + email + " not found");
		}
	}

	/**
	 * Get a user by its username
	 *
	 * @param username - the user's username
	 * @return User - requested user
	 */
	public UserDto findByUsername(String username) {
		Optional<Users> requestedUser = this.userRepository.findByUsername(username);
		if (requestedUser.isPresent()) {
			Users user = requestedUser.get();
			return user.generateDto();
		} else {
			throw new ResourceNotFoundException("User with username " + username + " not found");
		}
	}

	/**
	 * Update an existing User
	 *
	 * @param dto - User objet to be updated
	 * @return User - updated User
	 */
	public UserDto update(UpdateUserDto dto) {
		if (this.userRepository.existsById(dto.id())) {
			Users updated = this.userRepository.save(dto.updateUser());
			return updated.generateDto();
		} else {
			throw new ResourceNotFoundException("User with id " + dto.id() + " not found");
		}
	}

	/**
	 * Change password for authenticated user
	 *
	 * @param dto - map containing old and new password
	 * @return map containing success message
	 */
	public UserDto changePassword(LoginRequest dto) {
		Users user = userRepository.findByEmail(dto.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("User with email " + dto.getEmail() + " not found"));

		if (!passwordEncoder.matches(dto.getPassword(), user.getPasswordHash())) {
			throw new RuntimeException("Invalid current password");
		}

		user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
		Users saved = userRepository.save(user);

		return saved.generateDto();
	}

	/**
	 * Delete a user by its ID
	 *
	 * @param id - the user's ID
	 */
	public void delete(Long id) {
		this.userRepository.delete(
				this.userRepository.findById(id).orElseThrow(() ->
						new ResourceNotFoundException("User with id " + id + " not found")
				)
		);
	}

	/**
	 * Restore a soft-deleted user by its ID
	 *
	 * @param id - the user's ID
	 * @return User - restored user
	 */
	public UserDto restoreById(Long id) {
		Optional<Users> restoredUser = this.userRepository.restoreById(id);
		if (restoredUser.isPresent()) {
			Users user = restoredUser.get();
			return user.generateDto();
		} else {
			throw new ResourceNotFoundException("User with id " + id + " not found or not deleted");
		}
	}
}
