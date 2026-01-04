package com.carloswimmer.todolist.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByUsername(String username);
}
