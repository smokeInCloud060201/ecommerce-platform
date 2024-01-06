CREATE TABLE otp
(
    id          BIGINT    NOT NULL PRIMARY KEY,
    code        TEXT      NOT NULL check ( code <> '' ),
    expired_at  TIMESTAMP NOT NULL,
    is_verified BOOLEAN   NOT NULL DEFAULT FALSE,
    is_deleted  BOOLEAN   NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP NOT NULL DEFAULT now(),
    updated_at  TIMESTAMP NOT NULL DEFAULT now()
);

ALTER TABLE otp
    ADD COLUMN user_id BIGINT;

ALTER TABLE otp
    ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id);