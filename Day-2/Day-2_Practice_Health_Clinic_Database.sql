USE health_clinic_db;

-- Drop Day 1 basic tables to rebuild normalized version
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS patients;
DROP TABLE IF EXISTS doctors;
SET FOREIGN_KEY_CHECKS = 1;

-- 1. Patients (3NF compliant)
CREATE TABLE patients (
patient_id INT AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
date_of_birth DATE,
gender ENUM('Male', 'Female', 'Other'),
email VARCHAR(100) UNIQUE,
registered_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- 2. Patient Phones (1NF fix -- multi-valued attribute separated)
DROP TABLE IF EXISTS patient_phones;
CREATE TABLE patient_phones (
phone_id INT AUTO_INCREMENT PRIMARY KEY,
patient_id INT NOT NULL,
phone_number VARCHAR(15) NOT NULL,
FOREIGN KEY (patient_id) REFERENCES patients(patient_id) ON DELETE CASCADE,
INDEX idx_patient_id (patient_id)
);
-- 3. Doctors (3NF compliant -- specialization moved out, see below)
CREATE TABLE doctors (
doctor_id INT AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(50) NOT NULL,
last_name VARCHAR(50) NOT NULL,
phone_number VARCHAR(15) UNIQUE,
email VARCHAR(100) UNIQUE
);
-- 4. Specializations (separate entity)
DROP TABLE IF EXISTS specializations;
CREATE TABLE specializations (
specialization_id INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(100) NOT NULL UNIQUE,
description VARCHAR(255)
);
-- 5. Doctor <-> Specialization (M:N junction table)
CREATE TABLE doctor_specializations (
doctor_id INT,
specialization_id INT,
PRIMARY KEY (doctor_id, specialization_id),
FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE,
FOREIGN KEY (specialization_id) REFERENCES specializations(specialization_id) ON DELETE
CASCADE
);
-- 6. Appointments (core transactional table)
DROP TABLE IF EXISTS appointments;
CREATE TABLE appointments (
appointment_id INT AUTO_INCREMENT PRIMARY KEY,
patient_id INT NOT NULL,
doctor_id INT NOT NULL,
appointment_date DATETIME NOT NULL,
status ENUM('Scheduled', 'Completed', 'Cancelled') DEFAULT 'Scheduled',
FOREIGN KEY (patient_id) REFERENCES patients(patient_id),
FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id),
INDEX idx_patient_id (patient_id),
INDEX idx_doctor_date (doctor_id, appointment_date) -- composite index
);

-- 7. Billing (1:1 with Appointment)
CREATE TABLE billing (
bill_id INT AUTO_INCREMENT PRIMARY KEY,
appointment_id INT NOT NULL UNIQUE, -- UNIQUE enforces 1:1
amount DECIMAL(10,2) NOT NULL,
payment_status ENUM('Pending', 'Paid', 'Refunded') DEFAULT 'Pending',
billing_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id)
);
-- 8. Visit History (1:1 with Appointment)
CREATE TABLE visit_history (
visit_id INT AUTO_INCREMENT PRIMARY KEY,
appointment_id INT NOT NULL UNIQUE, -- UNIQUE enforces 1:1
diagnosis VARCHAR(255),
prescription VARCHAR(255),
visit_notes TEXT,
FOREIGN KEY (appointment_id) REFERENCES appointments(appointment_id)
);
-- Sample Data
INSERT INTO patients (first_name, last_name, date_of_birth, gender, email)
VALUES ('Ramesh', 'Kumar', '1979-05-14', 'Male', 'ramesh@email.com');
INSERT INTO patient_phones (patient_id, phone_number) VALUES (1, '9876543210'), (1, '9998887777');
INSERT INTO doctors (first_name, last_name, phone_number, email)
VALUES ('Anjali', 'Rao', '9123456780', 'dr.rao@clinic.com');
INSERT INTO specializations (name, description)
VALUES ('Cardiology', 'Heart-related treatment'), ('Pediatrics', 'Child healthcare');
INSERT INTO doctor_specializations (doctor_id, specialization_id) VALUES (1, 1);
INSERT INTO appointments (patient_id, doctor_id, appointment_date, status)
VALUES (1, 1, '2026-08-05 10:00:00', 'Scheduled');
INSERT INTO billing (appointment_id, amount, payment_status)
VALUES (1, 1500.00, 'Pending');
INSERT INTO visit_history (appointment_id, diagnosis, prescription, visit_notes)
VALUES (1, 'Routine Checkup', 'None', 'Patient in good health');

-- Verify with a join
SELECT p.first_name, p.last_name, d.first_name AS doctor_name,
a.appointment_date, b.amount, v.diagnosis
FROM appointments a
JOIN patients p ON a.patient_id = p.patient_id
JOIN doctors d ON a.doctor_id = d.doctor_id
JOIN billing b ON a.appointment_id = b.appointment_id
JOIN visit_history v ON a.appointment_id = v.appointment_id;




