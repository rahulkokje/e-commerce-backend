--liquibase formatted sql
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

-- changeset rahulkokje:3
create materialized view user_credits_view
as
	select t.user_id as user_id, sum(t.amount) as credits
	from transactions t
	group by t.user_id;

create or replace function tg_refresh_user_credits_view()
returns trigger language plpgsql as $$
begin
    refresh materialized view concurrently user_credits_view;
    return null;
end
$$;

create trigger tg_refresh_user_credits_view after insert
on transactions
for each statement execute procedure tg_refresh_user_credits_view();

-- changeset rahulkokje:4
CREATE UNIQUE INDEX ON user_credits_view (user_id);