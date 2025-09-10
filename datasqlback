-- Users
INSERT INTO user (email, password, role) VALUES
('admin@canteen.com', '$2a$10$8UGiNs0BvdV0sa1VPrUX4OFjaKC610wILyJL5WBlM4ErC5lXO05Zm', 'ADMIN'),
('john.doe@example.com', '$2a$10$8UGiNs0BvdV0sa1VPrUX4OFjaKC610wILyJL5WBlM4ErC5lXO05Zm', 'CUSTOMER'),
('jane.smith@example.com', '$2a$10$8UGiNs0BvdV0sa1VPrUX4OFjaKC610wILyJL5WBlM4ErC5lXO05Zm', 'CUSTOMER');

-- Admin (link to user 1)
INSERT INTO admin (name, phone, user_id) VALUES
('Super Admin', 9876543210, 1);

-- Customers (link to users 2,3)
INSERT INTO customer (name, phone, user_id) VALUES
('John Doe', 9123456780, 2),
('Jane Smith', 9876501234, 3);

-- Canteens
INSERT INTO canteen (name) VALUES
('Main Campus Canteen'),
('Engineering Block Canteen');

-- Categories
INSERT INTO category (image_url, name, canteen_id) VALUES
('https://example.com/images/beverages.png', 'Beverages', 1),
('https://example.com/images/snacks.png', 'Snacks', 2);

-- Items
INSERT INTO item (image_url, is_available, name, price, canteen_id, category_id) VALUES
('https://example.com/images/coffee.png', b'1', 'Coffee', 50, 1, 1),
('https://example.com/images/tea.png', b'1', 'Tea', 30, 1, 1),
('https://example.com/images/samosa.png', b'1', 'Samosa', 20, 2, 2),
('https://example.com/images/sandwich.png', b'0', 'Sandwich', 40, 2, 2);

-- Sub Item Types
INSERT INTO sub_item_type (extra_price, name, item_id) VALUES
(10, 'Extra Milk', 1),
(5, 'Ginger', 2),
(8, 'Extra Sauce', 3);

-- Orders
INSERT INTO orders (created_at, item_count, status, total, canteen_id, customer_id) VALUES
(NOW(), 2, 'CREATED', 100, 1, 1),
(NOW(), 3, 'CONFIRMED', 90, 2, 2);

-- Order Items
INSERT INTO order_item (quantity, item_id, order_id, sub_item_type_id) VALUES
(2, 1, 1, 1),
(1, 3, 1, 3),
(3, 2, 2, 2);

-- Cart Items
INSERT INTO cart_item (quantity, customer_id, item_id) VALUES
(2, 1, 1),
(1, 1, 3),
(3, 2, 2);

-- Images
INSERT INTO image (image_url) VALUES
('https://example.com/images/banner1.png'),
('https://example.com/images/banner2.png');