-- changeset rahulkokje:1
DROP table IF EXISTS order_items;
DROP table IF EXISTS orders;

CREATE TABLE t_order(
    id SERIAL PRIMARY KEY,
    order_number varchar(40)
);

CREATE TABLE t_order_item(
    id SERIAL PRIMARY KEY,
    sku_code varchar(50),
    price DECIMAL,
    quantity INTEGER,
    order_id INTEGER,
    CONSTRAINT fk_order_id FOREIGN KEY(order_id) REFERENCES t_order(id)
);