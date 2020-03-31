CREATE OR REPLACE PROCEDURE setup_fbanking()
LANGUAGE PLPGSQL AS $$
BEGIN
	DELETE FROM movements WHERE 1 = 1;
    DELETE FROM accounts WHERE 2 = 2;
    DELETE FROM customers WHERE 3 = 3;
    DELETE FROM banks WHERE 4 = 4;

    INSERT INTO banks (cvr, name) VALUES ('bid123', 'Slybank');

    INSERT INTO customers (cpr, name, fk_bank_cvr) VALUES 
    ('id123', 'Jacob', 'bid123'), 
    ('id321', 'Nikolaj', 'bid123'),
    ('id420', 'Stephan', 'bid123');

    INSERT INTO accounts (number, fk_customer_cpr) VALUES 
    ('42069', 'id123'),
    ('src123', 'id123'),
    ('trgt132', 'id123');

	INSERT INTO movements (amount, source, target, time) VALUES 
	(420, '42069', 'src123', NOW()),
	(80, '42069', 'src123', NOW()),
	(300, 'src123', '42069', NOW());

END;
$$;

