package io.wintech.sssecurity.model.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Entity
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Admin {

    @Id
    @Column(name = "admin_uuid")
    @Builder.Default
    private UUID adminUuid = UUID.randomUUID();
    private String username;
    private String password;
    private String email;
    @Column(name = "first_name")
    private String firstname;
    @Column(name = "last_name")
    private String lastname;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    private String country;
    @Column(name = "last_login")
    private LocalDateTime lastLogin;
    private LocalDateTime closed;

    @OneToMany(fetch = FetchType.EAGER)
    @Builder.Default
    @JoinColumn(name = "role_name")
    private Set<AccessRole> roles = new HashSet<>();

    public boolean hasPermission(String permission) {
        return roles.stream()
                .flatMap(role -> role.getAccessTypes().stream())
                .anyMatch(accessType -> accessType.equals(new AccessType(permission)));
    }

}
