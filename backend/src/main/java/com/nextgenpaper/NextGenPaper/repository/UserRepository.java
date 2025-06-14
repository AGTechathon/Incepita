package com.nextgenpaper.NextGenPaper.repository;

import com.nextgenpaper.NextGenPaper.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    public Optional<User> findByEmail(String email);

    public Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}
