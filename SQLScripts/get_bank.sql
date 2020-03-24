CREATE OR REPLACE FUNCTION get_bank(arg_cvr varchar)
RETURNS SETOF "banks" AS $$
BEGIN

	RETURN QUERY SELECT * FROM "banks" as b WHERE b.cvr = arg_cvr;

END;
$$ LANGUAGE PLPGSQL;