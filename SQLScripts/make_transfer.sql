CREATE OR REPLACE PROCEDURE make_transfer(
	amount bigint, 
	source varchar,
	target varchar)

LANGUAGE PLPGSQL AS $$
BEGIN
    INSERT INTO movement (amount, source, target, time) VALUES (amount, source, target, NOW());
END;
$$;

