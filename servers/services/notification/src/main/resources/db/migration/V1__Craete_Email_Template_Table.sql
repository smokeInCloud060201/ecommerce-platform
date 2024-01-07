CREATE TABLE email_template
(
    id         BIGINT    NOT NULL PRIMARY KEY,
    type       TEXT      NOT NULL UNIQUE CHECK ( type <> '' ),
    subject    TEXT      NOT NULL CHECK ( subject <> '' ),
    message    TEXT      NOT NULL CHECK ( message <> '' ),
    is_deleted BOOLEAN   NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT now(),
    updated_at TIMESTAMP NOT NULL DEFAULT now()
);

INSERT INTO email_template(id, type, subject, message)
VALUES (1632515470363120444, 'OTP', 'Verify OTP',
        'Hi, \n Your code is: {otp}. Use it to verify your account. \n\n If you did not request this, simply ignore this message. \n\n Best regards, \n The Karson Ecommerce Team')
ON CONFLICT (id) DO NOTHING;