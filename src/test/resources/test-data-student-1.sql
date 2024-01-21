INSERT INTO "users" (id, city, country, email, name, password, phone, street, surname, role, enabled, blocked)
VALUES (1, 'Berlin', 'Germany', 'stefandjurica420@gmail.com', 'Bob', '$2a$10$jmd0YlAdXmwMUC9fLtO9Ke2yr0JAakmg1EqbaezSfCaiLVSpwCWQi', '+49 30 9876 5432', '789 Oak St', 'Jones', 2, true, false);

INSERT INTO "users" (id, city, country, email, name, password, phone, street, surname, role, enabled, blocked)
VALUES (2, 'Berlin', 'Germany', 'stefandjurica69@gmail.com', 'Joe', '$2a$10$jmd0YlAdXmwMUC9fLtO9Ke2yr0JAakmg1EqbaezSfCaiLVSpwCWQi', '+49 30 9876 5432', '789 Oak St', 'Bones', 1, true, false);


INSERT INTO accommodation (id, owner_id, name, type, min_guests, max_guests, description, days_for_cancellation, location, enabled, per_night,auto_accepting)
VALUES (1, 1, 'Cozy Cottage', 0, 2, 4, 'A lovely cottage in the woods', 7, 'Beograd', true, false,false);

INSERT INTO reservation (id, guests_number, price, status, end_date, start_date, accommodation_id, user_id)
VALUES (1, 3, 747, 0, 1706702400000, 1706443200000, 1, 2);

INSERT INTO accommodation_pricing (id, price, end_date, start_date, accommodation_id)
VALUES (1, 747, 1706356800000, 1706184000000, 1);