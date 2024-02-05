-- changeset rahulkokje:1
CREATE TABLE transactions (
    id SERIAL PRIMARY KEY,
    user_id VARCHAR(40),
    amount FLOAT,
    source VARCHAR(20),
    transaction_time TIMESTAMP
);

-- changeset rahulkokje:2
ALTER TABLE transactions ADD COLUMN transaction_type VARCHAR(8);