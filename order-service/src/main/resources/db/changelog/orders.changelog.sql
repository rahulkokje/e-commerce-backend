-- liquibase formatted sql
-- changeset rahulkokje:1
DROP table IF EXISTS t_order_item;
DROP table IF EXISTS t_order;

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

-- changeset rahulkokje:2
ALTER TABLE t_order ADD COLUMN status varchar(40);