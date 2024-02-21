package io.wintech.sssecurity.repository;

import io.wintech.sssecurity.model.admin.AccessType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccessTypeRepository extends JpaRepository<AccessType, String> {
    Optional<AccessType> findByName(String name);
    List<AccessType> findByNameIgnoreCaseIn(List<String> names);
}
