-- liquibase formatted sql
-- changeset "Ifeanyichukwu Otiwa":"init_user_schema"

CREATE TABLE IF NOT EXISTS customer
(
    user_uuid   BINARY(16) PRIMARY KEY,
    username   VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    phone_number VARCHAR(80) NOT NULL UNIQUE,
    country    VARCHAR(255) NOT NULL,
    enabled    BOOLEAN DEFAULT FALSE,
    created_at DATETIME DEFAULT NOW(),
    deactivated_at DATETIME DEFAULT NULL,
    account_closed DATETIME DEFAULT NULL,
    banned BOOLEAN DEFAULT FALSE,
    CONSTRAINT uq_customer_username UNIQUE (username),
    CONSTRAINT uq_customer_email UNIQUE (email),
    CONSTRAINT uq_customer_brand UNIQUE (phone_number, country)
)