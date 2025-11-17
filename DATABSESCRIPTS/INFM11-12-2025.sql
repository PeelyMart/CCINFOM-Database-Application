-- Create database
CREATE DATABASE IF NOT EXISTS restaurantdb
  DEFAULT CHARACTER SET utf8mb4
  COLLATE utf8mb4_0900_ai_ci;

USE restaurantdb;

-- ----------------------
-- Table: staff
-- ----------------------
CREATE TABLE IF NOT EXISTS staff (
    staff_id INT NOT NULL AUTO_INCREMENT,
    staff_pin INT NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    contact_number VARCHAR(11) NOT NULL,
    PRIMARY KEY (staff_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


-- ----------------------
-- Table: staff_tracker
-- ----------------------
CREATE TABLE IF NOT EXISTS staff_tracker (
    session_id INT NOT NULL AUTO_INCREMENT,
    staff_id INT NOT NULL,
    date DATE NOT NULL,
    time_in DATETIME DEFAULT NULL,
    time_out DATETIME DEFAULT NULL,
    session_minutes INT DEFAULT NULL,
    PRIMARY KEY (session_id),
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------
-- Table: tables
-- ----------------------
CREATE TABLE IF NOT EXISTS tables (
    table_id INT NOT NULL AUTO_INCREMENT,
    capacity INT NOT NULL,
    is_available TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (table_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------
-- Table: loyalty_members
-- ----------------------
CREATE TABLE IF NOT EXISTS loyalty_members (
    customer_id INT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    contact_number VARCHAR(15),
    join_date DATE DEFAULT NULL,
    points INT DEFAULT 0,
    is_active TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------
-- Table: menu_items
-- ----------------------
CREATE TABLE IF NOT EXISTS menu_items (
    menu_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    description TEXT DEFAULT NULL,
    price DECIMAL(10,2) NOT NULL,
    is_available TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------
-- Table: order_header
-- ----------------------
CREATE TABLE IF NOT EXISTS order_header (
    order_id INT NOT NULL AUTO_INCREMENT,
    table_id INT NOT NULL,
    staff_id INT NOT NULL,
    order_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    total_cost DECIMAL(10,2) DEFAULT 0.00,
    status ENUM('closed','open','cancelled') DEFAULT 'open',
    PRIMARY KEY (order_id),
    FOREIGN KEY (table_id) REFERENCES tables(table_id),
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------
-- Table: order_item
-- ----------------------
CREATE TABLE IF NOT EXISTS order_item (
    order_item_id INT NOT NULL AUTO_INCREMENT,
    order_id INT NOT NULL,
    menu_id INT NOT NULL,
    quantity INT NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    is_active TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (order_item_id),
    FOREIGN KEY (order_id) REFERENCES order_header(order_id),
    FOREIGN KEY (menu_id) REFERENCES menu_items(menu_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------
-- Table: payments
-- ----------------------
CREATE TABLE IF NOT EXISTS payments (
    transaction_id INT NOT NULL AUTO_INCREMENT,
    order_id INT NOT NULL,
    amount_paid DECIMAL(10,2) NOT NULL,
    staff_id INT NOT NULL,
    loyal_customer_id INT DEFAULT NULL,
    unknown_customer_name VARCHAR(50) DEFAULT NULL,
    payment_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    payment_method ENUM('CASH','DEBIT','CREDIT') NOT NULL,
    is_active TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (transaction_id),
    FOREIGN KEY (order_id) REFERENCES order_header(order_id),
    FOREIGN KEY (staff_id) REFERENCES staff(staff_id),
    FOREIGN KEY (loyal_customer_id) REFERENCES loyalty_members(customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ----------------------
-- Table: reservations
-- ----------------------
CREATE TABLE IF NOT EXISTS reservations (
    request_id INT NOT NULL AUTO_INCREMENT,
    table_id INT NOT NULL,
    reserve_name VARCHAR(50) NOT NULL,
    date_and_time DATETIME NOT NULL,
    is_active TINYINT(1) NOT NULL DEFAULT 1,
    PRIMARY KEY (request_id),
    FOREIGN KEY (table_id) REFERENCES tables(table_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

