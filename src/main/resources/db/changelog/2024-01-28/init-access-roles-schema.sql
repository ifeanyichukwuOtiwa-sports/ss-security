-- liquibase formatted sql
-- changeset "Ifeanyichukwu Otiwa":"init_access_roles_schema"


CREATE TABLE IF NOT EXISTS access_role (
    name VARCHAR(255) NOT NULL UNIQUE,
    fk_admin_id BINARY(16),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_at TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_role_name (name)
);