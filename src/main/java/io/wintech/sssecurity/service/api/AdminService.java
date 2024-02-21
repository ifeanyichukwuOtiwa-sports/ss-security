package io.wintech.sssecurity.service.api;

import io.wintech.sssecurity.model.admin.Admin;
import io.wintech.sssecurity.service.dto.EditAdminDetailRequest;
import io.wintech.sssecurity.service.dto.SavedAdminDto;

import java.util.UUID;

public interface AdminService {

    SavedAdminDto createAdmin(String brand, String userName, String email, String firstName, String lastName);
    Admin editOwnPassword(UUID adminUuid, EditAdminDetailRequest request);
    Admin updatePassword(EditAdminDetailRequest request, Admin admin);
    void validatePassword(EditAdminDetailRequest request, Admin admin);
    Admin close(UUID adminUuid);
    Admin getAdminOrFail(UUID adminUuid);
    void sendPasswordEmail(String email, String username, String password);
    Admin updateAdmin(Admin admin);
}
