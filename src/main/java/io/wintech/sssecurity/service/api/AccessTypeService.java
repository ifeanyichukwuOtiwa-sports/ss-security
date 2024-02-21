package io.wintech.sssecurity.service.api;

import io.wintech.sssecurity.model.admin.AccessType;

import java.util.List;
import java.util.Set;

public interface AccessTypeService {
    Set<AccessType> saveAll(List<AccessType> accessTypes);
}
