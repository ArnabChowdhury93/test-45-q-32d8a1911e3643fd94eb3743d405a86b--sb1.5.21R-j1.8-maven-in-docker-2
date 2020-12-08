CREATE TABLE if not exists Lead_Info(
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    mobile VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    location_type ENUM('COUNTRY', 'CITY', 'ZIP'),
    location_string VARCHAR(255) NOT NULL,
    status ENUM('CREATED', 'CONTACTED'),
    communication VARCHAR(255) DEFAULT NULL,
    UNIQUE(mobile,email)
);
