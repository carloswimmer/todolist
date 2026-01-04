package com.carloswimmer.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import com.carloswimmer.todolist.ApiResponse;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "tb_users")
public class UserModel implements ApiResponse {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(unique = true)
    private String username;
    private String name;
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
