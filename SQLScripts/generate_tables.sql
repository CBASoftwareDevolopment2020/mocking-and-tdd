
DROP TABLE IF EXISTS "movements";
DROP TABLE IF EXISTS "accounts";
DROP TABLE IF EXISTS "customers";
DROP TABLE IF EXISTS "banks";

CREATE TABLE "banks" (
  "cvr" varchar PRIMARY KEY,
  "name" varchar
);

CREATE TABLE "customers" (
  "cpr" varchar PRIMARY KEY,
  "name" varchar,
  "fk_bank_cvr" varchar
);

CREATE TABLE "accounts" (
  "number" varchar PRIMARY KEY,
  "fk_customer_cpr" varchar
);

CREATE TABLE "movements" (
  "id" SERIAL PRIMARY KEY,
  "amount" bigint NOT NULL CHECK ("amount" > 0),
  "time" timestamp,
  "target" varchar,
  "source" varchar
);

ALTER TABLE "customers" ADD FOREIGN KEY ("fk_bank_cvr") REFERENCES "banks" ("cvr");

ALTER TABLE "accounts" ADD FOREIGN KEY ("fk_customer_cpr") REFERENCES "customers" ("cpr");

ALTER TABLE "movements" ADD FOREIGN KEY ("target") REFERENCES "accounts" ("number");

ALTER TABLE "movements" ADD FOREIGN KEY ("source") REFERENCES "accounts" ("number");
