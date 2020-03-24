CREATE OR REPLACE FUNCTION get_account(acc_number varchar)
RETURNS SETOF "accounts" AS $$
BEGIN

	RETURN QUERY SELECT * FROM "accounts" as a WHERE a.number = acc_number;

END;
$$ LANGUAGE PLPGSQL;