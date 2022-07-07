CREATE TABLE items
(
    id          SERIAL PRIMARY KEY,
    name        TEXT,
    description TEXT,
    created     TIMESTAMP,
    done        BOOLEAN,
    user_id     INT NOT NULL REFERENCES users (id)
);

CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    name     TEXT UNIQUE,
    password TEXT
);

CREATE TABLE categories
(
    id   SERIAL PRIMARY KEY,
    name TEXT UNIQUE
);

CREATE TABLE IF NOT EXISTS items_categories
(
    item_id       INT NOT NULL REFERENCES items (id),
    categories_id INT NOT NULL REFERENCES categories (id)
)