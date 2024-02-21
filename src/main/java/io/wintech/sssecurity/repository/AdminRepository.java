package io.wintech.sssecurity.repository;

import io.wintech.sssecurity.model.admin.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByUsernameIgnoreCaseOrEmailIgnoreCase(String username, String email);
}
