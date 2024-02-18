package io.wintech.sssecurity.repository;

import io.wintech.sssecurity.model.admin.Admin;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class AdminRepositoryTest extends BasePersistenceTest {


    @Test
    void saveAndFindByUuid() {
        final Admin admin = Admin.builder()
                .username("admin")
                .email("admin@example.com")
                .password("admin")
                .firstname("admin")
                .lastname("admin")
                .createdAt(LocalDateTime.now())
                .country("Nigeria")
                .build();

        final Admin savedEntity = adminRepository.save(admin);

        final Optional<Admin> optionalAdmin = adminRepository.findById(admin.getAdminUuid());

        assertThat(optionalAdmin).isPresent()
                .hasValueSatisfying(value -> assertThat(value)
                        .usingRecursiveComparison()
                        .isEqualTo(savedEntity)
                );
    }
}