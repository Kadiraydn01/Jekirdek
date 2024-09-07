package com.jekirdek.project.Repository;

import com.jekirdek.project.Entity.Role;
import com.jekirdek.project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User>findById(int id);
    Optional<User> findByUsername(String username);
    Optional<User> findByRole(Role role);
}
