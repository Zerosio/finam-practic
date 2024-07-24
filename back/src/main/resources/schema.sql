CREATE TABLE IF NOT EXISTS "firms" (
  "firm_id" serial PRIMARY KEY,
  "name" varchar,
  "ticker" varchar,
  "sector_id" int,
  "capitalization" float
);

CREATE TABLE IF NOT EXISTS "instrument_types" (
  "instrument_type_id" serial PRIMARY KEY,
  "instrument_type" varchar
);

CREATE TABLE IF NOT EXISTS "sectors" (
  "sector_id" serial PRIMARY KEY,
  "type_of_sector" varchar
);

CREATE TABLE IF NOT EXISTS "finance_instruments" (
  "finance_instrument_id" serial PRIMARY KEY,
  "firm_id" int,
  "instrument_type_id" int,
  "average_trading_volume" float,
  "price" float
);

ALTER TABLE "firms" ADD FOREIGN KEY ("sector_id") REFERENCES "sectors" ("sector_id");

ALTER TABLE "finance_instruments" ADD FOREIGN KEY ("instrument_type_id") REFERENCES "instrument_types" ("instrument_type_id");

ALTER TABLE "finance_instruments" ADD FOREIGN KEY ("firm_id") REFERENCES "firms" ("firm_id");

