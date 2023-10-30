ALTER TABLE   "impleme-bolite"."bolite"."booking" ADD COLUMN flag_week BOOLEAN;
UPDATE bolite.booking SET flag_week = false;
ALTER TABLE "impleme-bolite"."bolite"."booking" ALTER COLUMN flag_week SET NOT NULL;
ALTER TABLE
    "impleme-bolite"."bolite"."booking" ALTER COLUMN "booking_start_hour" DROP NOT NULL;
ALTER TABLE
    "impleme-bolite"."bolite"."booking" ALTER COLUMN "booking_end_hour" DROP NOT NULL;