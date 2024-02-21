package io.wintech.sssecurity.service.api;

import io.wintech.sssecurity.model.admin.AccessTypeUrl;

import java.util.Set;

public interface AccessTypeUrlService {
    Set<AccessTypeUrl> saveAllAccessTypesUrl(Set<String> urls);
}
