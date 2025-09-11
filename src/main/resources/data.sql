-- Users
INSERT INTO user (email, password, role) VALUES
('admin@canteen.com', '$2a$10$8UGiNs0BvdV0sa1VPrUX4OFjaKC610wILyJL5WBlM4ErC5lXO05Zm', 'ADMIN'),
('john.doe@example.com', '$2a$10$8UGiNs0BvdV0sa1VPrUX4OFjaKC610wILyJL5WBlM4ErC5lXO05Zm', 'CUSTOMER'),
('jane.smith@example.com', '$2a$10$8UGiNs0BvdV0sa1VPrUX4OFjaKC610wILyJL5WBlM4ErC5lXO05Zm', 'CUSTOMER');

-- Admin (linked to user_id 1)
INSERT INTO admin (name, phone, user_id) VALUES
('Super Admin', 9876543210, (SELECT id FROM user WHERE email='admin@canteen.com'));

-- Customers (linked to user_id)
INSERT INTO customer (name, phone, user_id) VALUES
('John Doe', 9123456780, (SELECT id FROM user WHERE email='john.doe@example.com')),
('Jane Smith', 9876501234, (SELECT id FROM user WHERE email='jane.smith@example.com'));

-- Canteens
INSERT INTO canteen (name) VALUES
('Main Campus Canteen'),
('Engineering Block Canteen');

-- Categories
INSERT INTO category (image_url, name, canteen_id) VALUES
('https://example.com/images/beverages.png', 'Beverages', (SELECT id FROM canteen WHERE name='Main Campus Canteen')),
('https://example.com/images/snacks.png', 'Snacks', (SELECT id FROM canteen WHERE name='Main Campus Canteen')),
('https://example.com/images/beverages.png', 'Beverages', (SELECT id FROM canteen WHERE name='Engineering Block Canteen')),
('https://example.com/images/snacks.png', 'Snacks', (SELECT id FROM canteen WHERE name='Engineering Block Canteen'));

-- Items
INSERT INTO item (image_url, is_available, name, price, canteen_id, category_id) VALUES
('https://example.com/images/coffee.png', b'1', 'Coffee', 50, (SELECT id FROM canteen WHERE name='Main Campus Canteen'), (SELECT id FROM category WHERE name='Beverages' AND canteen_id=(SELECT id FROM canteen WHERE name='Main Campus Canteen'))),
('https://example.com/images/tea.png', b'1', 'Tea', 30, (SELECT id FROM canteen WHERE name='Main Campus Canteen'), (SELECT id FROM category WHERE name='Beverages' AND canteen_id=(SELECT id FROM canteen WHERE name='Main Campus Canteen'))),
('https://example.com/images/samosa.png', b'1', 'Samosa', 20, (SELECT id FROM canteen WHERE name='Main Campus Canteen'), (SELECT id FROM category WHERE name='Snacks' AND canteen_id=(SELECT id FROM canteen WHERE name='Main Campus Canteen'))),
('https://example.com/images/sandwich.png', b'1', 'Sandwich', 40, (SELECT id FROM canteen WHERE name='Main Campus Canteen'), (SELECT id FROM category WHERE name='Snacks' AND canteen_id=(SELECT id FROM canteen WHERE name='Main Campus Canteen'))),
('https://example.com/images/espresso.png', b'1', 'Espresso', 60, (SELECT id FROM canteen WHERE name='Engineering Block Canteen'), (SELECT id FROM category WHERE name='Beverages' AND canteen_id=(SELECT id FROM canteen WHERE name='Engineering Block Canteen'))),
('https://example.com/images/juice.png', b'1', 'Juice', 35, (SELECT id FROM canteen WHERE name='Engineering Block Canteen'), (SELECT id FROM category WHERE name='Beverages' AND canteen_id=(SELECT id FROM canteen WHERE name='Engineering Block Canteen'))),
('https://example.com/images/pizza.png', b'1', 'Pizza Slice', 70, (SELECT id FROM canteen WHERE name='Engineering Block Canteen'), (SELECT id FROM category WHERE name='Snacks' AND canteen_id=(SELECT id FROM canteen WHERE name='Engineering Block Canteen'))),
('https://example.com/images/burger.png', b'1', 'Veg Burger', 45, (SELECT id FROM canteen WHERE name='Engineering Block Canteen'), (SELECT id FROM category WHERE name='Snacks' AND canteen_id=(SELECT id FROM canteen WHERE name='Engineering Block Canteen')));

-- Sub Item Types
INSERT INTO sub_item_type (extra_price, name, item_id) VALUES
(10, 'Extra Milk', (SELECT id FROM item WHERE name='Coffee')),
(5, 'Ginger', (SELECT id FROM item WHERE name='Tea')),
(8, 'Extra Sauce', (SELECT id FROM item WHERE name='Samosa')),
(7, 'Cheese', (SELECT id FROM item WHERE name='Sandwich'));

-- Orders
INSERT INTO orders (created_at, item_count, status, total, canteen_id, customer_id) VALUES
(DATE_SUB(NOW(), INTERVAL 1 DAY), 2, 'CREATED', 100, (SELECT id FROM canteen WHERE name='Main Campus Canteen'), (SELECT id FROM customer WHERE name='John Doe')),
(DATE_SUB(NOW(), INTERVAL 2 DAY), 3, 'CONFIRMED', 150, (SELECT id FROM canteen WHERE name='Engineering Block Canteen'), (SELECT id FROM customer WHERE name='Jane Smith')),
(DATE_SUB(NOW(), INTERVAL 3 DAY), 1, 'PREPARING', 50, (SELECT id FROM canteen WHERE name='Main Campus Canteen'), (SELECT id FROM customer WHERE name='John Doe')),
(DATE_SUB(NOW(), INTERVAL 4 DAY), 2, 'RECEIVED', 120, (SELECT id FROM canteen WHERE name='Engineering Block Canteen'), (SELECT id FROM customer WHERE name='Jane Smith'));

-- Order Items
INSERT INTO order_item (quantity, item_id, order_id, sub_item_type_id) VALUES
(2, (SELECT id FROM item WHERE name='Coffee'), (SELECT id FROM orders WHERE customer_id=(SELECT id FROM customer WHERE name='John Doe') ORDER BY created_at LIMIT 1), (SELECT id FROM sub_item_type WHERE name='Extra Milk')),
(1, (SELECT id FROM item WHERE name='Samosa'), (SELECT id FROM orders WHERE customer_id=(SELECT id FROM customer WHERE name='John Doe') ORDER BY created_at LIMIT 1), (SELECT id FROM sub_item_type WHERE name='Extra Sauce')),
(3, (SELECT id FROM item WHERE name='Juice'), (SELECT id FROM orders WHERE customer_id=(SELECT id FROM customer WHERE name='Jane Smith') ORDER BY created_at LIMIT 1), NULL),
(1, (SELECT id FROM item WHERE name='Pizza Slice'), (SELECT id FROM orders WHERE customer_id=(SELECT id FROM customer WHERE name='Jane Smith') ORDER BY created_at LIMIT 1), NULL),
(1, (SELECT id FROM item WHERE name='Tea'), (SELECT id FROM orders WHERE customer_id=(SELECT id FROM customer WHERE name='John Doe') ORDER BY created_at LIMIT 1), (SELECT id FROM sub_item_type WHERE name='Ginger')),
(2, (SELECT id FROM item WHERE name='Veg Burger'), (SELECT id FROM orders WHERE customer_id=(SELECT id FROM customer WHERE name='Jane Smith') ORDER BY created_at LIMIT 1), NULL);

-- Cart Items
INSERT INTO cart_item (quantity, customer_id, item_id, sub_item_type_id) VALUES
(2, (SELECT id FROM customer WHERE name='John Doe'), (SELECT id FROM item WHERE name='Coffee'), (SELECT id FROM sub_item_type WHERE name='Extra Milk')),
(1, (SELECT id FROM customer WHERE name='John Doe'), (SELECT id FROM item WHERE name='Samosa'), (SELECT id FROM sub_item_type WHERE name='Extra Sauce')),
(2, (SELECT id FROM customer WHERE name='John Doe'), (SELECT id FROM item WHERE name='Tea'), (SELECT id FROM sub_item_type WHERE name='Ginger')),
(1, (SELECT id FROM customer WHERE name='John Doe'), (SELECT id FROM item WHERE name='Sandwich'), NULL),
(3, (SELECT id FROM customer WHERE name='Jane Smith'), (SELECT id FROM item WHERE name='Juice'), NULL),
(1, (SELECT id FROM customer WHERE name='Jane Smith'), (SELECT id FROM item WHERE name='Pizza Slice'), NULL),
(2, (SELECT id FROM customer WHERE name='Jane Smith'), (SELECT id FROM item WHERE name='Coffee'), (SELECT id FROM sub_item_type WHERE name='Extra Milk')),
(1, (SELECT id FROM customer WHERE name='Jane Smith'), (SELECT id FROM item WHERE name='Tea'), (SELECT id FROM sub_item_type WHERE name='Ginger'));

-- Images
INSERT INTO image (image_url) VALUES
('https://example.com/images/banner1.png'),
('https://example.com/images/banner2.png'),
('https://example.com/images/banner3.png');
