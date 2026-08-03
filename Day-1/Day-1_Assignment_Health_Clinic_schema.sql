-- Day 1 - Assignment
-- 1. Verifying mySQL version
SELECT VERSION();

-- 2. Using heath table
CREATE DATABASE IF NOT EXISTS health_clinic_db;
USE health_clinic_db;

-- 3. Creating specializations table and appointments table
CREATE TABLE IF NOT EXISTS specializations (
id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL,
description TEXT);
 
CREATE TABLE IF NOT EXISTS appointments (
id INT AUTO_INCREMENT PRIMARY KEY,
patient_id INT,
doctor_id INT,
appointment_date DATE);

-- 4. Inserting data into both tables
INSERT INTO specializations (name, description)
VALUES ('Cardiology', 'Heart and blood vessel specialist'),
('Dermatology', 'Skin, hair, and nail specialist');

INSERT INTO appointments (patient_id, doctor_id, appointment_date)
VALUES (1, 101, '2026-07-31'),
(2, 102, '2026-08-01');

-- 5. Altering table to add and drop column
ALTER TABLE specializations ADD COLUMN experience_years INT;
DESCRIBE doctors;

ALTER TABLE specializations DROP COLUMN experience_years;
DESCRIBE doctors;

-- 6. One UPDATE and one DELETE query with proper WHERE clauses, and verify results with SELECT before and after.
SELECT * FROM specializations;

UPDATE specializations 
SET description = 'Specialist in skin disorders'
WHERE name = 'Dermatology';

SELECT * FROM specializations;

DELETE FROM appointments 
WHERE id = 2;

SELECT * FROM appointments;

-- 7. A new MySQL user clinic_app_user with SELECT, INSERT, UPDATE, DELETE privileges only on health_clinic_db
CREATE USER 'clinic_app_user2'@'localhost' IDENTIFIED BY 'StrongPassword123';

GRANT SELECT, INSERT, UPDATE, DELETE 
ON health_clinic_db.* 
TO 'clinic_app_user2'@'localhost';

FLUSH PRIVILEGES;
SHOW GRANTS FOR 'clinic_app_user'@'localhost';





