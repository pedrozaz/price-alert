CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    url VARCHAR(1024) NOT NULL UNIQUE,
    current_price NUMERIC(19,2),
    store VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS alert (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    target_price NUMERIC(19,2),
    created_at TIMESTAMP,
    notified BOOLEAN,
    store_name VARCHAR(255),
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS price_history (
    id SERIAL PRIMARY KEY,
    product_id INTEGER NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    price VARCHAR(255),
    date_time TIMESTAMP
);