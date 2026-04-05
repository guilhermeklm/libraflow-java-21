CREATE TABLE authors
(
    id   UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE books
(
    id               UUID PRIMARY KEY,
    isbn             VARCHAR(13)  NOT NULL UNIQUE,
    title            VARCHAR(500) NOT NULL,
    publisher        VARCHAR(255),
    publication_year INTEGER,
    genre            VARCHAR(50)  NOT NULL,
    total_copies     INTEGER      NOT NULL DEFAULT 0,
    available_copies INTEGER      NOT NULL DEFAULT 0
);

CREATE TABLE book_authors
(
    book_id   UUID NOT NULL REFERENCES books (id) ON DELETE CASCADE,
    author_id UUID NOT NULL REFERENCES authors (id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, author_id)
);