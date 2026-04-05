CREATE TABLE loans
(
    id            UUID PRIMARY KEY,
    reader_id     UUID        NOT NULL REFERENCES users (id),
    book_id       UUID        NOT NULL REFERENCES books (id),
    loan_date     TIMESTAMP   NOT NULL,
    due_date      DATE        NOT NULL,
    returned_at   TIMESTAMP,
    status        VARCHAR(50) NOT NULL,
    renewal_count INTEGER     NOT NULL DEFAULT 0
);

CREATE TABLE reservations
(
    id             UUID PRIMARY KEY,
    reader_id      UUID        NOT NULL REFERENCES users (id),
    book_id        UUID        NOT NULL REFERENCES books (id),
    reserved_at    TIMESTAMP   NOT NULL,
    expires_at     TIMESTAMP,
    status         VARCHAR(50) NOT NULL,
    queue_position INTEGER     NOT NULL
);
