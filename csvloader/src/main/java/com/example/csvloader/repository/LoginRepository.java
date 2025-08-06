package com.example.csvloader.repository;

import com.example.csvloader.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByAppAccountName(String name);
}
