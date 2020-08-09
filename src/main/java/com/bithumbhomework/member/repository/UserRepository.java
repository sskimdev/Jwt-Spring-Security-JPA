package com.bithumbhomework.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bithumbhomework.member.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Boolean existsByEmail(String email);

	Optional<User> findByEmail(String email);

	Boolean existsByUsername(String username);
}
