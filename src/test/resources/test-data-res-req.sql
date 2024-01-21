-- INSERT INTO "users" (id, city, country, email, name, password, phone, street, surname, role, enabled, blocked)
-- VALUES (2, 'Berlin', 'Germany', 'bob.jones@example.com', 'Bob', '$2a$10$CUvvlUoIyBbwE2cTnb3.sObysf0Cni4Dd5/GIgOY965J/Ycv/abhW', '+49 30 9876 5432', '789 Oak St', 'Jones', 2, true, false);
-- INSERT INTO accommodation (id, owner_id, name, type, min_guests, max_guests, description, days_for_cancellation, location, enabled, per_night,auto_accepting)
-- VALUES (1, 2, 'Cozy Cottage', 0, 2, 4, 'A lovely cottage in the woods', 7, 'Beograd', true, false,false);
INSERT INTO reservation_request (id,accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (1,1, 3, 150.00, 1, 1643673600, 1644595200, 2);
INSERT INTO reservation_request (id,accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (2,1, 3, 150.00, 1, 1643673600, 1644595200, 1);
INSERT INTO reservation_request (id,accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (3,1, 3, 150.00, 1, 1643673600, 1644595200, 1);
INSERT INTO reservation_request (id,accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (4,1, 3, 150.00, 1, 1643673600, 1644595200, 1);