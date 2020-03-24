CREATE OR REPLACE PROCEDURE add_customer(cpr VARCHAR(10), name VARCHAR(64), fk_bank_cvr VARCHAR)
LANGUAGE PLPGSQL AS $$
BEGIN
    INSERT INTO customers (cpr, name, fk_bank_cvr) VALUES (cpr, name, fk_bank_cvr);
END;
$$;

