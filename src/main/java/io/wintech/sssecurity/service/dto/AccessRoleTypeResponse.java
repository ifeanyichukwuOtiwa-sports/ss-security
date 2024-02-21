package io.wintech.sssecurity.service.dto;

import com.opencsv.bean.exceptionhandler.CsvExceptionHandler;
import io.wintech.sssecurity.model.admin.AccessType;

import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public record AccessRoleTypeResponse(
        Set<String> enabledAccessTypes,
        Set<String> disabledAccessTypes
) {
    public static AccessRoleTypeResponse from(Set<AccessType> enabledAccessTypes, Set<AccessType> disabledAccessTypes) {
        final Function<Set<AccessType>, Set<String>> mapToResponse = accessTypes -> accessTypes.stream()
                .map(AccessType::getName)
                .collect(Collectors.toSet());
        return new AccessRoleTypeResponse(mapToResponse.apply(enabledAccessTypes), mapToResponse.apply(disabledAccessTypes));
    }
}
