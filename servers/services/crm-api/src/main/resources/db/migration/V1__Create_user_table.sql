CREATE TABLE users
(
    id                         BIGINT      NOT NULL PRIMARY KEY,
    email                      TEXT UNIQUE NOT NULL CHECK ( email <> '' ),
    password                   TEXT        NOT NULL CHECK (password <> '' ),
    is_verified                BOOLEAN     NOT NULL DEFAULT FALSE,
    is_enabled                 BOOLEAN     NOT NULL DEFAULT TRUE,
    is_account_non_expired     BOOLEAN     NOT NULL DEFAULT TRUE,
    is_account_non_locked      BOOLEAN     NOT NULL DEFAULT TRUE,
    is_credentials_non_expired BOOLEAN     NOT NULL DEFAULT TRUE,
    is_deleted                 BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at                 TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at                 TIMESTAMP   NOT NULL DEFAULT now()
);

CREATE TABLE roles
(
    id         BIGINT      NOT NULL PRIMARY KEY,
    name       TEXT UNIQUE NOT NULL CHECK ( name <> '' ),
    is_deleted BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at TIMESTAMP   NOT NULL DEFAULT now()
);

CREATE TABLE permissions
(
    id         BIGINT      NOT NULL PRIMARY KEY,
    name       TEXT UNIQUE NOT NULL CHECK ( name <> '' ),
    is_deleted BOOLEAN     NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP   NOT NULL DEFAULT now(),
    updated_at TIMESTAMP   NOT NULL DEFAULT now()
);

CREATE TABLE roles_permissions
(
    role_id        BIGINT REFERENCES roles (id),
    permissions_id BIGINT REFERENCES permissions (id),
    CONSTRAINT roles_permissions_pk PRIMARY KEY (role_id, permissions_id)
);

CREATE TABLE users_roles
(
    user_id BIGINT REFERENCES users (id),
    role_id BIGINT REFERENCES roles (id),
    CONSTRAINT users_roles_pk PRIMARY KEY (role_id, user_id)
);