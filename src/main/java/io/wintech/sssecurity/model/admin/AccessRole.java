package io.wintech.sssecurity.model.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Table(name = "access_role")
public class AccessRole {

    @Id
    @Column(unique = true, name = "role_name")
    private String name;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "modified_at")
    private Instant modifiedAt;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "name")
    @Builder.Default
    private Set<AccessType> accessTypes = new HashSet<>();

    public boolean hasAccess(final String accessType) {
        return accessTypes.contains(new AccessType(accessType));
    }
}
