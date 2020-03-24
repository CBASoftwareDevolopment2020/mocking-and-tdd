CREATE OR REPLACE FUNCTION get_customer(arg_cpr varchar)
RETURNS SETOF "customers" AS $$
BEGIN

	RETURN QUERY SELECT * FROM "customers" as c WHERE c.cpr = arg_cpr;

END;
$$ LANGUAGE PLPGSQL;