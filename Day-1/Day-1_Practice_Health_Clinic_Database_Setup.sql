-- Creating database
CREATE DATABASE health_clinic_db;
USE health_clinic_db;
SHOW DATABASES;
DROP DATABASE health_clinic_db;

CREATE DATABASE health_clinic_db;
USE health_clinic_db;


-- Creating table
CREATE TABLE patients (
patient_id INT AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
date_of_birth DATE,
gender ENUM('Male', 'Female', 'Other'),
phone_number VARCHAR(15) UNIQUE,
email VARCHAR(100),
registered_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP );


-- DDL commands (Create, Alter, Drop, Truncate and Rename)
ALTER TABLE patients ADD COLUMN address VARCHAR(200);
ALTER TABLE patients MODIFY COLUMN phone_number VARCHAR(20);

ALTER TABLE patients CHANGE COLUMN address home_address VARCHAR(200);
ALTER TABLE patients DROP COLUMN home_address;

-- DROP TABLE patients;
-- TRUNCATE TABLE patients;


-- DML commands (Insert, Update and Delete)
INSERT INTO patients (first_name, last_name, date_of_birth, gender, phone_number, email)
VALUES ('Ramesh', 'Kumar', '1979-05-14', 'Male', '9876543210', 'ramesh@email.com');

INSERT INTO patients (first_name, last_name, date_of_birth, gender, phone_number, email)
VALUES
('Sita', 'Sharma', '1990-08-21', 'Female', '9876543211', 'sita@email.com'),
('Aman', 'Verma', '2001-01-30', 'Male', '9876543212', 'aman@email.com');

UPDATE patients
SET phone_number = '9998887777'
WHERE patient_id = 1;

DELETE FROM patients
WHERE patient_id = 3;


-- Start transaction (TCL)
UPDATE patients SET phone_number = '1112223333' WHERE patient_id = 1;
DELETE FROM patients WHERE patient_id = 99;
ROLLBACK;
COMMIT;


-- DCL commands
GRANT SELECT, INSERT ON health_clinic_db.* TO 'clinic_app_user'@'localhost';
REVOKE INSERT ON health_clinic_db.* FROM 'clinic_app_user'@'localhost';

CREATE USER 'clinic_app_user'@'localhost' IDENTIFIED BY 'StrongPassword123!';
GRANT SELECT, INSERT, UPDATE, DELETE ON health_clinic_db.* TO 'clinic_app_user'@'localhost';
FLUSH PRIVILEGES;





-- Day 1 Practice: Health Clinic Database Setup
CREATE TABLE doctors (
doctor_id INT AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
specialization VARCHAR(100),
phone_number VARCHAR(15) UNIQUE,
email VARCHAR(100)
);

INSERT INTO doctors (first_name, last_name, specialization, phone_number, email)
VALUES
('Anjali', 'Rao', 'Cardiology', '9123456780', 'dr.rao@clinic.com'),
('Vikram', 'Iyer', 'Pediatrics', '9123456781', 'dr.iyer@clinic.com');

SELECT * FROM patients;
SELECT * FROM doctors;




