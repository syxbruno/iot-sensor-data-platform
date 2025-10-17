CREATE TABLE Device (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    latitude DOUBLE,
    longitude DOUBLE,
    type VARCHAR(50),
    active BOOLEAN NOT NULL,
    registeredAt DATETIME,
    updatedAt DATETIME
);