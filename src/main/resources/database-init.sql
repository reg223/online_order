-- =========================================================================
-- 1. DROP TABLES (Ordered correctly by dependencies to avoid CASCADE)
-- =========================================================================
DROP TABLE IF EXISTS order_items;
DROP TABLE IF EXISTS menu_items;
DROP TABLE IF EXISTS shops;
DROP TABLE IF EXISTS carts;
DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS customers;

-- =========================================================================
-- 2. CREATE TABLES
-- =========================================================================

CREATE TABLE customers
(
    id         SERIAL  PRIMARY KEY  NOT NULL,
    email      TEXT    UNIQUE       NOT NULL,
    enabled    BOOLEAN DEFAULT TRUE NOT NULL,
    password   TEXT                 NOT NULL,
    first_name TEXT,
    last_name  TEXT
);

CREATE TABLE authorities
(
    id         SERIAL  PRIMARY KEY  NOT NULL,
    email      TEXT                 NOT NULL,
    authority  TEXT                 NOT NULL,
    CONSTRAINT fk_customer FOREIGN KEY (email) REFERENCES customers (email) ON DELETE CASCADE
);

CREATE TABLE carts
(
    id          SERIAL  PRIMARY KEY  NOT NULL,
    customer_id INTEGER UNIQUE       NOT NULL,
    price       NUMERIC              NOT NULL,
    CONSTRAINT  fk_customer FOREIGN KEY (customer_id) REFERENCES customers (id) ON DELETE CASCADE
);

CREATE TABLE shops
(
    id          SERIAL  PRIMARY KEY  NOT NULL,
    name        TEXT                 NOT NULL,
    address     TEXT,
    image_url   TEXT,
    phone       TEXT
);

CREATE TABLE menu_items
(
    id          SERIAL  PRIMARY KEY  NOT NULL,
    shop_id     INTEGER              NOT NULL,
    name        TEXT                 NOT NULL,
    price       NUMERIC              NOT NULL,
    description TEXT,
    image_url   TEXT,
    CONSTRAINT  fk_shop FOREIGN KEY (shop_id) REFERENCES shops (id) ON DELETE CASCADE
);

CREATE TABLE order_items
(
    id           SERIAL  PRIMARY KEY  NOT NULL,
    menu_item_id INTEGER              NOT NULL,
    cart_id      INTEGER              NOT NULL,
    price        NUMERIC              NOT NULL,
    quantity     INTEGER              NOT NULL,
    CONSTRAINT   fk_cart FOREIGN KEY (cart_id) REFERENCES carts (id) ON DELETE CASCADE,
    CONSTRAINT   fk_menu_item FOREIGN KEY (menu_item_id) REFERENCES menu_items (id) ON DELETE CASCADE
);