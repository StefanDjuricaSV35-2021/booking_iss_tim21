-- Inserting users with adjusted ids
INSERT INTO "users" (id, city, country, email, name, password, phone, street, surname, role, enabled)
VALUES (7, 'New York', 'USA', 'john.doe@example.com', 'John', '$2a$10$CUvvlUoIyBbwE2cTnb3.sObysf0Cni4Dd5/GIgOY965J/Ycv/abhW', '+1 123 456 7890', '123 Main St', 'Doe', 0, true);

INSERT INTO "users" (id, city, country, email, name, password, phone, street, surname, role, enabled)
VALUES (8, 'London', 'UK', 'jane.smith@example.com', 'Jane', '$2a$10$CUvvlUoIyBbwE2cTnb3.sObysf0Cni4Dd5/GIgOY965J/Ycv/abhW', '+44 20 1234 5678', '456 Park Ave', 'Smith', 0, true);

INSERT INTO "users" (id, city, country, email, name, password, phone, street, surname, role, enabled)
VALUES (9, 'Berlin', 'Germany', 'bob.jones@example.com', 'Bob', '$2a$10$CUvvlUoIyBbwE2cTnb3.sObysf0Cni4Dd5/GIgOY965J/Ycv/abhW', '+49 30 9876 5432', '789 Oak St', 'Jones', 2, true);

-- Inserting additional users with adjusted ids
INSERT INTO "users" (id, city, country, email, name, password, phone, street, surname, role, enabled)
VALUES (10, 'New York', 'USA', 'user102@example.com', 'User', '$2a$10$CUvvlUoIyBbwE2cTnb3.sObysf0Cni4Dd5/GIgOY965J/Ycv/abhW', '+1 987 654 3210', '456 Oak St', 'Lastname', 0, true);

INSERT INTO "users" (id, city, country, email, name, password, phone, street, surname, role, enabled)
VALUES (11, 'London', 'UK', 'user103@example.com', 'Another', '$2a$10$CUvvlUoIyBbwE2cTnb3.sObysf0Cni4Dd5/GIgOY965J/Ycv/abhW', '+44 20 9876 5432', '789 Elm St', 'Person', 0, true);

INSERT INTO "users" (id, city, country, email, name, password, phone, street, surname, role, enabled)
VALUES (12, 'Berlin', 'Germany', 'admin@example.com', 'Admin', '$2a$10$CUvvlUoIyBbwE2cTnb3.sObysf0Cni4Dd5/GIgOY965J/Ycv/abhW', '+49 30 1234 5678', '101 Maple St', 'Administrator', 2, true);


-- Inserting data into the accommodation table with modified fields
INSERT INTO accommodation (id, owner_id, name, type, min_guests, max_guests, description, days_for_cancellation, location, enabled, per_night)
VALUES (1, 9, 'Cozy Cottage', 0, 2, 4, 'A lovely cottage in the woods', 7, 'Beograd', true, false);

INSERT INTO accommodation (id, owner_id, name, type, min_guests, max_guests, description, days_for_cancellation, location, enabled, per_night)
VALUES (2, 12, 'Modern Apartment', 1, 1, 2, 'A sleek apartment in the city center', 14, 'Novi Sad', true, false);

INSERT INTO accommodation (id, owner_id, name, type, min_guests, max_guests, description, days_for_cancellation, location, enabled, per_night)
VALUES (3, 12, 'Seaside Villa', 2, 6, 10, 'A luxurious villa with a view of the ocean', 30, 'Nis', true, false);



-- Inserting amenities for the Cozy Cottage (accommodation_id = 1)
INSERT INTO accommodation_amenities (accommodation_id, amenities)
VALUES (1, 'WiFi');

INSERT INTO accommodation_amenities (accommodation_id, amenities)
VALUES (1, 'Parking');

-- Inserting amenities for the Modern Apartment (accommodation_id = 2)
INSERT INTO accommodation_amenities (accommodation_id, amenities)
VALUES (2, 'WiFi');

INSERT INTO accommodation_amenities (accommodation_id, amenities)
VALUES (2, 'TV');

-- Inserting amenities for the Seaside Villa (accommodation_id = 3)
INSERT INTO accommodation_amenities (accommodation_id, amenities)
VALUES (3, 'WiFi');

INSERT INTO accommodation_amenities (accommodation_id, amenities)
VALUES (3, 'Parking');

INSERT INTO accommodation_amenities (accommodation_id, amenities)
VALUES (3, 'TV');


-- Inserting photos for the Cozy Cottage (accommodation_id = 1)
INSERT INTO accommodation_photos (accommodation_id, photos)
VALUES (1, 'peakpx.jpg');

INSERT INTO accommodation_photos (accommodation_id, photos)
VALUES (1, 'peakpx.jpg');

-- Inserting photos for the Modern Apartment (accommodation_id = 2)
INSERT INTO accommodation_photos (accommodation_id, photos)
VALUES (2, 'peakpx.jpg');

INSERT INTO accommodation_photos (accommodation_id, photos)
VALUES (2, 'peakpx.jpg');

-- Inserting photos for the Seaside Villa (accommodation_id = 3)
INSERT INTO accommodation_photos (accommodation_id, photos)
VALUES (3, './src/main/resources/images/peakpx.jpg');

INSERT INTO accommodation_photos (accommodation_id, photos)
VALUES (3, './src/main/resources/images/peakpx.jpg');


-- Inserting pricing for the Cozy Cottage (accommodation_id = 1)
INSERT INTO accommodation_pricing (accommodation_id, price, start_date, end_date)
VALUES (1, 100.00, 1640995200, 1643673600); -- Jan 1, 2022 to Jan 31, 2022

-- Inserting pricing for the Modern Apartment (accommodation_id = 2)
INSERT INTO accommodation_pricing (accommodation_id, price, start_date, end_date)
VALUES (2, 150.00, 1640995200, 1643673600); -- Jan 1, 2022 to Jan 31, 2022

-- Inserting pricing for the Seaside Villa (accommodation_id = 3)
INSERT INTO accommodation_pricing (accommodation_id, price, start_date, end_date)
VALUES (3, 300.00, 1640995200, 1643673600); -- Jan 1, 2022 to Jan 31, 2022


----------------------------------------------


-- Inserting reviews for the Cozy Cottage (accommodation_id = 1)
INSERT INTO review (type,comment, rating, accommodation_id, user_id)
VALUES ('ACCOMMODATION','Wonderful place to stay!', 5, 1, 11);

INSERT INTO review (type,comment, rating, accommodation_id, user_id)
VALUES ('ACCOMMODATION','Nice and cozy, perfect for a getaway', 4, 1, 10);

-- Inserting reviews for the Modern Apartment (accommodation_id = 2)
INSERT INTO review (type,comment, rating, accommodation_id, user_id)
VALUES ('ACCOMMODATION','Very modern and clean', 5, 2, 7);

INSERT INTO review (type,comment, rating, accommodation_id, user_id)
VALUES ('ACCOMMODATION','Could be better', 3, 2, 7);

-- Inserting reviews for the Seaside Villa (accommodation_id = 3)
INSERT INTO review (type,comment, rating, accommodation_id, user_id)
VALUES ('ACCOMMODATION','Absolutely stunning views!', 5, 3, 9);

INSERT INTO review (type,comment, rating, accommodation_id, user_id)
VALUES ('ACCOMMODATION','Overpriced for what it offers', 2, 3, 11);

-----------------------------------------------

-- Adding favorite accommodations for users
INSERT INTO favorite_accommodation (accommodation_id, user_id)
VALUES (1, 9); -- Cozy Cottage for User 9

INSERT INTO favorite_accommodation (accommodation_id, user_id)
VALUES (2, 8); -- Modern Apartment for User 8

INSERT INTO favorite_accommodation (accommodation_id, user_id)
VALUES (3, 11); -- Seaside Villa for User 11

-- Additional favorites for users
INSERT INTO favorite_accommodation (accommodation_id, user_id)
VALUES (2, 9); -- Modern Apartment for User 9

INSERT INTO favorite_accommodation (accommodation_id, user_id)
VALUES (1, 7); -- Cozy Cottage for User 7

INSERT INTO favorite_accommodation (accommodation_id, user_id)
VALUES (3, 11); -- Seaside Villa for User 11

------------------------------------------------

-- Inserting reservation request notification for User 12
INSERT INTO notification (message, type, user_id)
VALUES ('New reservation request for Cozy Cottage', 'RESERVATION_REQUEST', 12);

-- Inserting reservation cancellation notification for User 7
INSERT INTO notification (message, type, user_id)
VALUES ('Reservation for Modern Apartment canceled', 'RESERVATION_CANCELLATION', 7);

-- Inserting owner review notification for User 7
INSERT INTO notification (message, type, user_id)
VALUES ('You have a new review for your property', 'OWNER_REVIEW', 7);

-- Inserting accommodation review notification for User 9
INSERT INTO notification (message, type, user_id)
VALUES ('New review for Modern Apartment', 'ACCOMMODATION_REVIEW', 9);

-- Inserting reservation request response notification for User 11
INSERT INTO notification (message, type, user_id)
VALUES ('Your reservation request for Seaside Villa has been accepted', 'RESERVATION_REQUEST_RESPONSE', 11);


-----------------------------------------

-- Inserting owner reviews
INSERT INTO review (type,comment, rating, owner_id, user_id)
VALUES ('OWNER','Great host, very responsive!', 5, 11, 12); -- User 11 reviewing User 12

INSERT INTO review (type,comment, rating, owner_id, user_id)
VALUES ('OWNER','Could improve communication', 3, 9, 8); -- User 9 reviewing User 8

INSERT INTO review (type,comment, rating, owner_id, user_id)
VALUES ('OWNER','Fantastic property management!', 5, 10, 7); -- User 10 reviewing User 7


----------------------------------------

-- Inserting reservations
INSERT INTO reservation (accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (1, 2, 120.00, 1, 1640995200, 1643673600, 7); -- Reservation for Cozy Cottage by User 7

INSERT INTO reservation (accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (2, 1, 150.00, 0, 1640995200, 1643673600, 11); -- Reservation for Modern Apartment by User 11

INSERT INTO reservation (accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (3, 6, 300.00, 1, 1640995200, 1643673600, 9); -- Reservation for Seaside Villa by User 9

-- Additional reservations
INSERT INTO reservation (accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (2, 1, 150.00, 1, 1640995200, 1643673600, 9); -- Reservation for Modern Apartment by User 9

INSERT INTO reservation (accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (1, 3, 180.00, 0, 1640995200, 1643673600, 10); -- Reservation for Cozy Cottage by User 10

INSERT INTO reservation (accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (3, 8, 400.00, 1, 1640995200, 1643673600, 8); -- Reservation for Seaside Villa by User 8

-------------------------------------


-- Inserting reservation requests
INSERT INTO reservation_request (accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (1, 3, 150.00, 1, 1643673600, 1644595200, 9); -- Request for Cozy Cottage by User 9

INSERT INTO reservation_request (accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (2, 2, 200.00, 0, 1644595200, 1645516800, 8); -- Request for Modern Apartment by User 8

INSERT INTO reservation_request (accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (3, 5, 350.00, 1, 1645516800, 1646424000, 8); -- Request for Seaside Villa by User 8

-- Additional reservation requests
INSERT INTO reservation_request (accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (1, 2, 120.00, 2, 1646424000, 1647331200, 7); -- Request for Cozy Cottage by User 7

INSERT INTO reservation_request (accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (2, 1, 150.00, 1, 1647331200, 1648238400, 11); -- Request for Modern Apartment by User 11

INSERT INTO reservation_request (accommodation_id, guests_number, price, status, start_date, end_date, user_id)
VALUES (3, 4, 300.00, 3, 1648238400, 1649068800, 12); -- Request for Seaside Villa by User 12


-------------------------------------

-- Inserting review reports
INSERT INTO review_report (description, review_id, user_id)
VALUES ('Inappropriate language used in the review', 1, 10); -- Report for Accommodation Review ID 1 by User 10

INSERT INTO review_report (description, review_id, user_id)
VALUES ('False information in the review', 2, 11); -- Report for Accommodation Review ID 2 by User 11

INSERT INTO review_report (description, review_id, user_id)
VALUES ('Suspicious activity in the review', 3, 11); -- Report for Accommodation Review ID 3 by User 11

-- Additional review reports
INSERT INTO review_report (description, review_id, user_id)
VALUES ('Review contains offensive content', 4, 9); -- Report for Accommodation Review ID 4 by User 9

INSERT INTO review_report (description, review_id, user_id)
VALUES ('Inappropriate behavior mentioned in the review', 5, 8); -- Report for Accommodation Review ID 5 by User 8

INSERT INTO review_report (description, review_id, user_id)
VALUES ('Unfair rating in the review', 6, 7); -- Report for Accommodation Review ID 6 by User 7


------------------------------------
-- Inserting user reports
INSERT INTO user_report (description, reported_id, reporter_id)
VALUES ('Spammy behavior', 7, 8); -- Report for User 7 by User 8

INSERT INTO user_report (description, reported_id, reporter_id)
VALUES ('Inappropriate content', 9, 10); -- Report for User 9 by User 10

INSERT INTO user_report (description, reported_id, reporter_id)
VALUES ('Harassment', 11, 12); -- Report for User 11 by User 12