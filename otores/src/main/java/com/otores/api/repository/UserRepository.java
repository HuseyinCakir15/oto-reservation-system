package com.otores.api.repository;

import com.otores.api.entity.User;
import com.otores.api.entity.Role;
import com.otores.api.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    List<User> findByRole(Role role);
    Optional<User> findByPhone(String phone);
    List<User> findByStatus(Status status);
}
