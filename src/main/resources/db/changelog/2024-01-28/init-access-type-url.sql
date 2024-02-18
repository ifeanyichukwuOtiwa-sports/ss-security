-- liquibase formatted sql
-- changeset "Ifeanyichukwu Otiwa":"init_access_type_url_schema"

CREATE TABLE IF NOT EXISTS access_type_url (
    url VARCHAR(255) PRIMARY KEY,
    fk_access_type VARCHAR(255),
    INDEX idx_url (url)
);