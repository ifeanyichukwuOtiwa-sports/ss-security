package io.wintech.sssecurity.model.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "access_type_url")
@Getter
@Setter
@EqualsAndHashCode(of = "url")
public class AccessTypeUrl {
    @Id
    private String url;
}
