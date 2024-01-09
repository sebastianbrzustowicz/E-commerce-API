USE shopAPI;

-- === users table ===
CREATE TABLE IF NOT EXISTS  shopAPI.users (
	userID VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phoneNum INT NOT NULL,
    role VARCHAR(50) NOT NULL,
    accCreated DATETIME NOT NULL
);
-- === products table ===
CREATE TABLE IF NOT EXISTS shopAPI.products (
    productID VARCHAR(36) PRIMARY KEY,
    productName VARCHAR(255) NOT NULL UNIQUE,
    price DECIMAL(10, 2) NOT NULL,
    description VARCHAR(500) NOT NULL,
    category VARCHAR(50) NOT NULL,
    availableQuantity INT NOT NULL,
    imagePath VARCHAR(255)
);
-- === orders table ===
CREATE TABLE IF NOT EXISTS shopAPI.orders (
    userID VARCHAR(36) NULL,
    orderID VARCHAR(36) PRIMARY KEY,
    orderType VARCHAR(50) NOT NULL,
    additionalInfo VARCHAR(255),
    registrationTime DATETIME NULL,
    FOREIGN KEY (userId) REFERENCES shopAPI.users(userID)
);
-- === orderedProducts table ===
CREATE TABLE IF NOT EXISTS shopAPI.orderedProducts (
	orderID VARCHAR(36),
    productID VARCHAR(36),
    productName VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (orderID) REFERENCES shopAPI.orders(orderID),
    FOREIGN KEY (productID) REFERENCES shopAPI.products(productID)
);
-- === cart table ===
CREATE TABLE shopAPI.cart (
    cartID VARCHAR(36) PRIMARY KEY,
    userID VARCHAR(36),
    productID VARCHAR(36),
    quantity INT,
    FOREIGN KEY (userID) REFERENCES shopAPI.users(userID),
    FOREIGN KEY (productID) REFERENCES shopAPI.products(productID)
);
-- === reviews table ===
CREATE TABLE IF NOT EXISTS shopAPI.reviews (
    reviewID VARCHAR(36) PRIMARY KEY,
    productID VARCHAR(36),
    userID VARCHAR(36),
    comment VARCHAR(500) NOT NULL,
    rating INT NOT NULL,
    reviewTime DATETIME NOT NULL,
    FOREIGN KEY (productID) REFERENCES shopAPI.products(productID),
    FOREIGN KEY (userID) REFERENCES shopAPI.users(userID)
);

-- show tables
SELECT * FROM shopAPI.users;
SELECT * FROM shopAPI.products;
SELECT * FROM shopAPI.orders;
SELECT * FROM shopAPI.orderedProducts;
SELECT * FROM shopAPI.cart;
SELECT * FROM shopAPI.reviews;
-- clear tables
TRUNCATE shopAPI.reviews;
TRUNCATE shopAPI.cart;
TRUNCATE shopAPI.orderedProducts;
TRUNCATE shopAPI.orders;
TRUNCATE shopAPI.products;
TRUNCATE shopAPI.users;
-- drop tables
DROP TABLE shopAPI.reviews;
DROP TABLE shopAPI.cart;
DROP TABLE shopAPI.orderedProducts;
DROP TABLE shopAPI.orders;
DROP TABLE shopAPI.products;
DROP TABLE shopAPI.users;

-- example inserts
INSERT INTO shopAPI.users (userID, name, email, password, phoneNum, role, accCreated)
VALUES
('1', 'John Doe', 'john@example.com', 'hashed_password', 123456789, 'customer', '2024-01-07 12:00:00'),
('2', 'Jane Smith', 'jane@example.com', 'hashed_password_2', 987654321, 'admin', '2024-01-08 10:30:00');

INSERT INTO shopAPI.products (productID, productName, price, description, category, availableQuantity, imagePath)
VALUES
('prod1', 'Laptop XYZ', 1299.99, 'High-performance laptop with advanced features.', 'Electronics', 10, '/images/laptop_xyz.jpg'),
('prod2', 'T-shirt Blue', 19.99, 'Comfortable cotton t-shirt in blue color.', 'Apparel', 50, '/images/tshirt_blue.jpg'),
('prod3', 'Book: Bestseller', 29.99, 'A bestselling book with intriguing content.', 'Books', 30, '/images/bestseller_book.jpg');

INSERT INTO shopAPI.orders (userID, orderID, orderType, additionalInfo, registrationTime)
VALUES
('1', 'order123', 'Online', 'Express Shipping', '2024-01-07 15:45:00'),
('2', 'order456', 'In-Store', 'Gift wrapping included', '2024-01-08 09:30:00');

INSERT INTO shopAPI.orderedProducts (orderID, productID, productName, price, quantity)
VALUES
('order123', 'prod1', 'Laptop XYZ', 1299.99, 2),
('order123', 'prod2', 'T-shirt Blue', 19.99, 1),
('order456', 'prod3', 'Book: Bestseller', 29.99, 3),
('order456', 'prod2', 'T-shirt Blue', 19.99, 1);

INSERT INTO shopAPI.cart (cartID, userID, productID, quantity)
VALUES
('1', '1', 'prod2', 2),
('2', '2', 'prod3', 1),
('3', '1', 'prod1', 3);

INSERT INTO shopAPI.reviews (reviewID, productID, userID, comment, rating, reviewTime)
VALUES
    ('review1', 'prod1', '1', 'Great product!', 5, '2024-01-09 12:30:00'),
    ('review2', 'prod1', '1', 'Excellent quality.', 4, '2024-01-10 14:45:00'),
    ('review3', 'prod2', '2', 'Not satisfied with the purchase.', 2, '2024-01-11 10:15:00'),
    ('review4', 'prod3', '2', 'Amazing book, highly recommend!', 5, '2024-01-12 09:00:00');