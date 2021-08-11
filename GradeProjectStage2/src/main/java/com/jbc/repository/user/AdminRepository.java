package com.jbc.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jbc.model.user.User;

/**
 * {@link com.jbc.model.user.User} {@code JpaRepository} that is using the
 * {@link User} {@code Entity} to manage the admins in the system.
 * 
 * @author Or Mizrahi
 * @author Shay Yadin
 * @author Jonathan Kaspi
 * @see user#User
 */
public interface AdminRepository extends JpaRepository<User, Long> {

	/* methods */
	public Optional<User> findByEmailIgnoreCaseAndPassword(String email, String password);

	public Optional<User> findByEmailIgnoreCase(String email);

}
