package io.wintech.sssecurity.model.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "userUuid")
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(columnNames = {"phone_number"}))
@Getter
@Setter
@ToString
public class Customer {

    @Id
    @Column(name = "user_uuid", nullable = false, length = 16)
    @Builder.Default
    private UUID userUuid = UUID.randomUUID();
    private String username;
    private String password;

    @Column(name = "phone_number")
    private String phoneNumber;
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;
    private String country;
    private boolean enabled;

    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "deactivated_at")
    private LocalDateTime deactivatedAt;
    @Column(name = "account_closed")
    private LocalDateTime accountClosed;
    private boolean banned;

    public boolean isAccountClosedInPastYear() {
        if (accountClosed == null) {
            return false;
        }
        return accountClosed.until(LocalDateTime.now(), ChronoUnit.YEARS) < 1;
    }

    public boolean isActive() {
        return !(banned || isAccountClosedInPastYear());
    }

}
