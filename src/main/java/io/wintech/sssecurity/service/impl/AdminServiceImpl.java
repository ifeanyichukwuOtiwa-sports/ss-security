package io.wintech.sssecurity.service.impl;

import io.wintech.sssecurity.exception.AppError;
import io.wintech.sssecurity.model.admin.Admin;
import io.wintech.sssecurity.repository.AdminRepository;
import io.wintech.sssecurity.service.api.AdminService;
import io.wintech.sssecurity.service.dto.EditAdminDetailRequest;
import io.wintech.sssecurity.service.dto.SavedAdminDto;
import io.wintech.sssecurity.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;
    @Override
    public SavedAdminDto createAdmin(final String country, final String username, final String email, final String firstname, final String lastname) {
        validateEmail(email);
        final Optional<Admin> existingAdminOptional = adminRepository.findByUsernameIgnoreCaseOrEmailIgnoreCase(username, email);

        if(existingAdminOptional.isPresent()) {
            throw AppError.ADMIN_DUPLICATE_ERROR.exception();
        }

        final String password = RandomStringUtils.random(10, true, true);
        final Admin admin = Admin.builder()
                .adminUuid(UUID.randomUUID())
                .email(email)
                .username(Optional.ofNullable(username).orElse(email))
                .firstname(firstname)
                .lastname(lastname)
                .password(PasswordUtil.hashPassword(password))
                .createdAt(LocalDateTime.now())
                .country(country)
                .roles(Set.of())
                .closed(null)
                .build();

        final Admin saved = adminRepository.save(admin);

        return new SavedAdminDto(saved.getEmail(), saved.getUsername(), saved.getPassword());
    }

    private void validateEmail(final String email) {
        final String emailRegex = "^(.+)@(.+)$";
        final Pattern pattern = Pattern.compile(emailRegex);

        if(!pattern.matcher(email).matches()) {
            throw AppError.ADMIN_INVALID_EMAIL_ERROR.exception();
        }
    }

    @Override
    public Admin editOwnPassword(final UUID adminUuid, final EditAdminDetailRequest request) {
        final Admin admin = getAdminOrFail(adminUuid);
        validatePassword(request, admin);
        return updatePassword(request, admin);
    }

    @Override
    public Admin updatePassword(final EditAdminDetailRequest request, final Admin admin) {
        if(!StringUtils.isBlank(request.newPassword())) {
            admin.setPassword(PasswordUtil.hashPassword(request.newPassword()));
        }

        admin.setFirstname(request.firstname());
        admin.setLastname(request.lastname());

        return adminRepository.save(admin);
    }

    @Override
    public void validatePassword(final EditAdminDetailRequest request, final Admin admin) {
        if (!StringUtils.isBlank(request.newPassword())) {
            final String oldCryptPassword = admin.getPassword();
            if (!PasswordUtil.checkPassword(request.password(), oldCryptPassword)) {
                throw AppError.ADMIN_WRONG_PASSWORD_ERROR.exception();
            }

            if (PasswordUtil.checkPassword(request.newPassword(), oldCryptPassword)) {
                throw AppError.ADMIN_NEW_PASSWORD_MUST_BE_DIFFERENT.exception();
            }
        }
    }

    @Override
    public Admin close(final UUID adminUuid) {
        final Admin admin = getAdminOrFail(adminUuid);
        admin.setClosed(LocalDateTime.now());
        return adminRepository.save(admin);
    }

    @Override
    public Admin getAdminOrFail(final UUID adminUuid) {
        return adminRepository.findById(adminUuid).orElseThrow(() -> {
            log.error("Admin not found with uuid {}", adminUuid);
            return AppError.ADMIN_NOT_FOUND_ERROR.exception();
        });
    }

    @Override
    public void sendPasswordEmail(final String email, final String username, final String password) {
        // TODO; would be completed after adding messaging API
    }

    @Override
    public Admin updateAdmin(final Admin admin) {
        return adminRepository.save(admin);
    }
}
