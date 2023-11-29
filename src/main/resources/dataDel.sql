-- Delete all rows from the accommodation_amenities table
DELETE FROM accommodation_amenities;

-- Delete all rows from the accommodation_photos table
DELETE FROM accommodation_photos;

-- Delete all rows from the accommodation_pricing table
DELETE FROM accommodation_pricing;

-- Delete all rows from the accommodation_review table
DELETE FROM accommodation_review;

-- Delete all rows from the favorite_accommodation table
DELETE FROM favorite_accommodation;

-- Delete all rows from the notification table
DELETE FROM notification;

-- Delete all rows from the owner_review table
DELETE FROM owner_review;

-- Delete all rows from the reservation table
DELETE FROM reservation;

-- Delete all rows from the reservation_request table
DELETE FROM reservation_request;

-- Delete all rows from the review_report table
DELETE FROM review_report;

-- Delete all rows from the "user" table
DELETE FROM "user";

-- Optionally, you may want to reset the sequence for any serial columns in your tables
-- ALTER SEQUENCE your_table_id_seq RESTART WITH 1;
