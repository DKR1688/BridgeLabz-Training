CREATE DATABASE IF NOT EXISTS health_clinic;
USE health_clinic;

CREATE TABLE IF NOT EXISTS specializations (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    specialization_id INT NOT NULL,
    phone VARCHAR(20),
    email VARCHAR(100),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    FOREIGN KEY (specialization_id) REFERENCES specializations(id)
);

CREATE TABLE IF NOT EXISTS patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    dob DATE,
    gender VARCHAR(20),
    phone VARCHAR(20),
    email VARCHAR(100),
    address VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    appointment_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    status VARCHAR(30) DEFAULT 'SCHEDULED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

CREATE TABLE IF NOT EXISTS billing (
    id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    payment_status VARCHAR(30) DEFAULT 'PENDING',
    payment_method VARCHAR(30) DEFAULT 'CASH',
    billed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id)
);

CREATE TABLE IF NOT EXISTS visit_history (
    id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id INT NOT NULL,
    patient_id INT NOT NULL,
    doctor_id INT NOT NULL,
    diagnosis VARCHAR(255),
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (appointment_id) REFERENCES appointments(id),
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

CREATE TABLE IF NOT EXISTS appointment_audit (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    appointment_id INT,
    action_type VARCHAR(20),
    old_status VARCHAR(30),
    new_status VARCHAR(30),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS billing_audit (
    audit_id INT AUTO_INCREMENT PRIMARY KEY,
    billing_id INT,
    action_type VARCHAR(20),
    old_status VARCHAR(30),
    new_status VARCHAR(30),
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

DELIMITER $$
CREATE TRIGGER IF NOT EXISTS appointment_status_audit
BEFORE UPDATE ON appointments
FOR EACH ROW
BEGIN
    INSERT INTO appointment_audit (appointment_id, action_type, old_status, new_status)
    VALUES (NEW.id, 'UPDATE', OLD.status, NEW.status);
END$$

CREATE TRIGGER IF NOT EXISTS billing_status_audit
BEFORE UPDATE ON billing
FOR EACH ROW
BEGIN
    INSERT INTO billing_audit (billing_id, action_type, old_status, new_status)
    VALUES (NEW.id, 'UPDATE', OLD.payment_status, NEW.payment_status);
END$$
DELIMITER ;

INSERT INTO specializations (name, description) VALUES
('Cardiology', 'Heart and circulation care'),
('Dermatology', 'Skin and dermatological care'),
('Orthopedics', 'Bone and joint specialist'),
('Neurology', 'Brain and nervous system care');

INSERT INTO doctors (first_name, last_name, specialization_id, phone, email) VALUES
('Dr. Asha', 'Kumar', 1, '9876543210', 'asha.kumar@clinic.com'),
('Dr. Mehul', 'Patel', 2, '9988776655', 'mehul.patel@clinic.com'),
('Dr. Rohan', 'Sharma', 3, '9765432100', 'rohan.sharma@clinic.com');

INSERT INTO patients (first_name, last_name, dob, gender, phone, email, address) VALUES
('Nisha', 'Verma', '1990-05-12', 'Female', '9123456780', 'nisha.verma@email.com', 'Bengaluru'),
('Arjun', 'Reddy', '1985-11-27', 'Male', '9234567890', 'arjun.reddy@email.com', 'Hyderabad'),
('Sonia', 'Patel', '1998-02-18', 'Female', '9345678901', 'sonia.patel@email.com', 'Pune');

INSERT INTO appointments (patient_id, doctor_id, appointment_date, start_time, end_time, status) VALUES
(1, 1, '2026-08-05', '09:00:00', '09:30:00', 'SCHEDULED'),
(2, 3, '2026-08-05', '11:00:00', '11:45:00', 'SCHEDULED'),
(3, 2, '2026-08-06', '14:00:00', '14:30:00', 'SCHEDULED');

INSERT INTO billing (appointment_id, amount, payment_status, payment_method) VALUES
(1, 1500.00, 'PENDING', 'CARD'),
(2, 2200.00, 'PENDING', 'UPI'),
(3, 1800.00, 'PENDING', 'CASH');

INSERT INTO visit_history (appointment_id, patient_id, doctor_id, diagnosis, notes) VALUES
(1, 1, 1, 'Routine check-up', 'Patient is stable and advised lifestyle modifications.'),
(2, 2, 3, 'Orthopedic review', 'Recovery noted, continue current treatment plan.'),
(3, 3, 2, 'Skin consultation', 'Prescribed topical medication and follow-up in 2 weeks.');

CREATE USER IF NOT EXISTS 'clinic_app_user'@'localhost' IDENTIFIED BY 'Clinic@123';
GRANT SELECT, INSERT, UPDATE, DELETE ON health_clinic.* TO 'clinic_app_user'@'localhost';
FLUSH PRIVILEGES;
