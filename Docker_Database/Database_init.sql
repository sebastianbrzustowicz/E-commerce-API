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