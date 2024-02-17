package io.wintech.sssecurity.model.admin;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "name")
@Getter
@Setter
@Entity
@Table(name = "access_type")
public class AccessType {

    @Id
    @Column(name = "name", unique = true)
    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "url")
    private Set<AccessTypeUrl> accessTypeUrls = new HashSet<>();

    public AccessType(final String name) {
        this.name = name;
        this.accessTypeUrls = Collections.emptySet();
    }
}
