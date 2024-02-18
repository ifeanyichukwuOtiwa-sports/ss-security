-- liquibase formatted sql
-- changeset "Ifeanyichukwu Otiwa":"init_admin_user_schema"

CREATE TABLE IF NOT EXISTS admin
(
    admin_uuid         BINARY(16) PRIMARY KEY,
    username   VARCHAR(255) UNIQUE,
    password   VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    created_at DATETIME NOT NULL,
    country VARCHAR(255) NOT NULL,
    last_login DATETIME,
    closed DATETIME DEFAULT NULL
)