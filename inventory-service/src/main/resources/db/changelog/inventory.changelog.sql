-- changelog rahul.kokje:1
CREATE TABLE inventory(
    id SERIAL PRIMARY KEY,
    sku_code VARCHAR(50),
    quantity INT
);