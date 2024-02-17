-- liquibase formatted sql
-- changeset "Ifeanyichukwu Otiwa":"init_access_types_schema"

CREATE TABLE IF NOT EXISTS access_type (
    name VARCHAR(255) PRIMARY KEY ,
    fk_access_role VARCHAR(255),
    INDEX idx_name(name)
);