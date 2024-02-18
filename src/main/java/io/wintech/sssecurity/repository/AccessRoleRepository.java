package io.wintech.sssecurity.repository;

import io.wintech.sssecurity.model.admin.AccessRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessRoleRepository extends JpaRepository<AccessRole, String> {
    Optional<AccessRole> findByName(String name);
}
