package io.wintech.sssecurity.model.admin;

import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

class AdminTest {

    @Test
    void hasPermission() {
        final String admin = "ADMIN";
        final String accessName = "read:api_test";
        final AccessTypeUrl typeUrl = new AccessTypeUrl("/api/test");
        final AccessType type = new AccessType(accessName, Set.of(typeUrl));
        final AccessRole accessRole = new AccessRole(admin, Instant.now(), null, Set.of(type));

        final LocalDateTime createdAt = LocalDateTime.now();
        final Admin testSample = Admin.builder()
                .username("admin")
                .email("admin@example.com")
                .password("admin")
                .firstname("admin")
                .lastname("admin")
                .createdAt(createdAt)
                .country("Nigeria")
                .roles(Set.of(accessRole))
                .build();

        assertThat(testSample)
                .satisfies(x -> {
                   assertThat(x.hasPermission("read:api_test")).isTrue();
                   assertThat(x.getCreatedAt()).isEqualToIgnoringSeconds(createdAt);
                   assertThat(x.getFirstname()).isEqualTo("admin");
                   assertThat(x.getLastname()).isEqualTo("admin");
                   assertThat(x.getRoles()).contains(accessRole);
                });
    }
}