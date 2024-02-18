package io.wintech.sssecurity.repository;

import io.wintech.sssecurity.model.admin.AccessTypeUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public interface AccessTypeUrlRepository extends JpaRepository<AccessTypeUrl, String> {

    Optional<AccessTypeUrl> findByUrl(String url);

    @Query(
            value =
                    """
                    SELECT atu.url
                    FROM access_type_url atu
                    INNER JOIN access_type at ON at.name = atu.fk_access_type
                    WHERE at.name = :accessTypeName
                    """, nativeQuery = true
    )
    List<String> findAllUrlsOfAccessType(final String accessTypeName);

    @Modifying
    @Query(
            value =
                    """
                    DELETE FROM access_type_url atu
                    WHERE atu.fk_access_type = :accessTypeName
                    """, nativeQuery = true
    )
    void deleteAllByAccessType(final String accessTypeName);
}
