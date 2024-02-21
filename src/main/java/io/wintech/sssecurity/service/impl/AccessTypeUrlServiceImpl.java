package io.wintech.sssecurity.service.impl;

import io.wintech.sssecurity.model.admin.AccessTypeUrl;
import io.wintech.sssecurity.repository.AccessTypeUrlRepository;
import io.wintech.sssecurity.service.api.AccessTypeUrlService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class AccessTypeUrlServiceImpl implements AccessTypeUrlService {
    private final AccessTypeUrlRepository accessTypeUrlRepository;
    @Override
    public Set<AccessTypeUrl> saveAllAccessTypesUrl(final Set<String> urls) {
        final List<AccessTypeUrl> accessTypeUrls = urls.stream()
                .map(AccessTypeUrl::new)
                .toList();
        return new HashSet<>(accessTypeUrlRepository.saveAll(accessTypeUrls));
    }
}
