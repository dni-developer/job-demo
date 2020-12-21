DROP TABLE IF EXISTS employee;

CREATE TABLE employee
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(250) NOT NULL,
    last_name  VARCHAR(250) NOT NULL,
    position   VARCHAR(250) DEFAULT NULL,
    source     VARCHAR(250) DEFAULT NULL
);
