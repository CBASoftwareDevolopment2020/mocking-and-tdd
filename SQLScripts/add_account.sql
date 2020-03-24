CREATE OR REPLACE PROCEDURE add_account(number VARCHAR(10), fk_customer_cpr VARCHAR)
LANGUAGE PLPGSQL AS $$
BEGIN
    INSERT INTO accounts (number, fk_customer_cpr) VALUES (number, fk_customer_cpr);
END;
$$;

