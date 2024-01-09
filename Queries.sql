USE shopAPI;

-- === users table ===
CREATE TABLE IF NOT EXISTS  shopAPI.users (
	userID VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    phoneNum INT NOT NULL,
    role VARCHAR(50) NOT NULL,
    accCreated DATETIME NOT NULL
);

-- === products table ===
CREATE TABLE IF NOT EXISTS shopAPI.products (
    productID VARCHAR(36) PRIMARY KEY,
    productName VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    description TEXT,
    category VARCHAR(50),
    availableQuantity INT NOT NULL,
    imagePath VARCHAR(255)
);

-- === orders table ===
CREATE TABLE IF NOT EXISTS shopAPI.orders (
    userID VARCHAR(36) NULL,
    orderID VARCHAR(36) PRIMARY KEY,
    orderType VARCHAR(50) NOT NULL,
    orderProducts VARCHAR(255) NOT NULL,
    additionalInfo VARCHAR(255),
    registrationTime DATETIME NULL,
    FOREIGN KEY (userId) REFERENCES shopAPI.users(userID)
);
-- === orderedProducts table ===
CREATE TABLE IF NOT EXISTS shopAPI.orderedProducts (
    orderedProductID VARCHAR(36) PRIMARY KEY,
    orderID VARCHAR(36),
    productName VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    amount INT NOT NULL,
    additionalInfo TEXT,
    FOREIGN KEY (orderID) REFERENCES shopAPI.orders(orderID)
);

-- show tables
SELECT * FROM shopAPI.users;
SELECT * FROM shopAPI.products;
SELECT * FROM shopAPI.orders;
SELECT * FROM shopAPI.orderedProducts;

-- clear tables
TRUNCATE shopAPI.users;
TRUNCATE shopAPI.products;
TRUNCATE shopAPI.orders;
TRUNCATE shopAPI.orderedProducts;
-- drop tables
DROP TABLE shopAPI.users;
DROP TABLE shopAPI.products;
DROP TABLE shopAPI.orders;
DROP TABLE shopAPI.orderedProducts;

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

INSERT INTO shopAPI.orders (userID, orderID, orderType, orderProducts, additionalInfo, registrationTime)
VALUES
('1', 'order123', 'Online', 'Product1, Product2', 'Express Shipping', '2024-01-07 15:45:00'),
('2', 'order456', 'In-Store', 'Product3, Product4, Product5', 'Gift wrapping included', '2024-01-08 09:30:00');

INSERT INTO shopAPI.orderedProducts (orderedProductID, orderID, productName, price, amount, additionalInfo)
VALUES
('product1', 'order123', 'ProductA', 19.99, 2, 'Color: Red'),
('product2', 'order123', 'ProductB', 29.99, 1, 'Size: Medium'),
('product3', 'order456', 'ProductC', 39.99, 3, 'Special Edition');