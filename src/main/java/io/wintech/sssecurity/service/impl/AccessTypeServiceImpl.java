package io.wintech.sssecurity.service.impl;

import io.wintech.sssecurity.model.admin.AccessType;
import io.wintech.sssecurity.repository.AccessTypeRepository;
import io.wintech.sssecurity.service.api.AccessTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccessTypeServiceImpl implements AccessTypeService {
    private final AccessTypeRepository accessTypeRepository;
    @Override
    public Set<AccessType> saveAll(final List<AccessType> accessTypes) {

        return new HashSet<>(accessTypeRepository.saveAll(accessTypes));
    }
}
