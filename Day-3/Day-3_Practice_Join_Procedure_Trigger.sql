use health_clinic_db;

-- Inner join
SELECT CONCAT(p.first_name, ' ', p.last_name) AS patient_name, a.appointment_date,
       CONCAT(d.first_name, ' ', d.last_name) AS doctor_name
FROM Appointments a
INNER JOIN Patients p ON a.patient_id = p.patient_id
INNER JOIN Doctors d ON a.doctor_id = d.doctor_id;

-- Left join
SELECT CONCAT(p.first_name, ' ', p.last_name) AS patient_name, a.appointment_date
FROM Patients p
LEFT JOIN Appointments a ON p.patient_id = a.patient_id;

-- Right join
SELECT CONCAT(d.first_name, ' ', d.last_name) AS doctor_name, a.appointment_date
FROM Appointments a
RIGHT JOIN Doctors d ON a.doctor_id = d.doctor_id;

-- Full outer join
SELECT CONCAT(p.first_name, ' ', p.last_name) AS patient_name, a.appointment_date
FROM Patients p
LEFT JOIN Appointments a ON p.patient_id = a.patient_id
UNION
SELECT CONCAT(p.first_name, ' ', p.last_name) AS patient_name, a.appointment_date
FROM Patients p
RIGHT JOIN Appointments a ON p.patient_id = a.patient_id;

-- Self join
SELECT CONCAT(d.first_name, ' ', d.last_name) AS doctor,
       CONCAT(m.first_name, ' ', m.last_name) AS mentor
FROM Doctors d
JOIN Doctors m ON d.mentor_id = m.doctor_id;

ALTER TABLE Doctors ADD mentor_id INT;
ALTER TABLE Doctors ADD FOREIGN KEY (mentor_id) REFERENCES Doctors(doctor_id);

-- Cross join
SELECT CONCAT(d.first_name, ' ', d.last_name) AS doctor, ts.slot_time
FROM Doctors d
CROSS JOIN TimeSlots ts;

CREATE TABLE TimeSlots (
slot_id INT PRIMARY KEY AUTO_INCREMENT,
slot_time TIME NOT NULL
);

INSERT INTO TimeSlots (slot_time) VALUES ('09:00:00'), ('10:00:00'), ('11:00:00');

-- Multiple table join
SELECT CONCAT(p.first_name, ' ', p.last_name) AS patient,
       CONCAT(d.first_name, ' ', d.last_name) AS doctor,
       s.name AS specialization, a.appointment_date, b.amount, b.payment_status
FROM Appointments a
JOIN Patients p ON a.patient_id = p.patient_id
JOIN Doctors d ON a.doctor_id = d.doctor_id
JOIN Specializations s ON d.specialization_id = s.specialization_id
LEFT JOIN Billing b ON a.appointment_id = b.appointment_id;

ALTER TABLE Doctors ADD specialization_id INT;
ALTER TABLE Doctors ADD FOREIGN KEY (specialization_id) REFERENCES Specializations(specialization_id);



-- Creating procedures
DELIMITER //
CREATE PROCEDURE GetPatientAppointments(IN pid INT)
BEGIN
SELECT a.appointment_date,
		CONCAT(d.first_name, ' ', d.last_name) AS doctor
FROM Appointments a
JOIN Doctors d ON a.doctor_id = d.doctor_id
WHERE a.patient_id = pid;
END //
DELIMITER ;

-- In parameter
DELIMITER //
CREATE PROCEDURE AddPatient(IN p_first VARCHAR(50), IN p_last VARCHAR(50), IN p_phone VARCHAR(15))
BEGIN
INSERT INTO Patients(first_name, last_name, phone_number)
VALUES (p_first, p_last, p_phone);
END //
DELIMITER ;

-- Out parameter
DELIMITER //
CREATE PROCEDURE GetPatientCount(OUT total INT)
BEGIN
SELECT COUNT(*) INTO total FROM Patients;
END //
DELIMITER ;

-- Inout parameter
DELIMITER //
CREATE PROCEDURE ApplyDiscount(INOUT amount DECIMAL(10,2))
BEGIN
SET amount = amount - (amount * 0.10);
END //
DELIMITER ;

-- Error handling
DELIMITER //
CREATE PROCEDURE SafeInsertPatient(IN p_first VARCHAR(50), IN p_last VARCHAR(50), IN p_phone VARCHAR(15))
BEGIN
DECLARE EXIT HANDLER FOR SQLEXCEPTION
BEGIN
	ROLLBACK;
	SELECT 'Error: Could not insert patient' AS message;
END;
START TRANSACTION;
INSERT INTO Patients(first_name, last_name, phone_number)
VALUES (p_first, p_last, p_phone);
COMMIT;
END //
DELIMITER ;



-- BEFORE INSERT on Appointments (validation)
DELIMITER //
CREATE TRIGGER before_appointment_insert
BEFORE INSERT ON Appointments
FOR EACH ROW
BEGIN
IF NEW.appointment_date < CURDATE() THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Cannot book an appointment in the past';
END IF;
END //
DELIMITER ;

-- AFTER INSERT on Appointments (audit log)  
CREATE TABLE AuditLog (
log_id INT AUTO_INCREMENT PRIMARY KEY,
action VARCHAR(20),
table_name VARCHAR(50),
record_id INT,
action_time TIMESTAMP
);

DELIMITER //
CREATE TRIGGER after_appointment_insert
AFTER INSERT ON Appointments
FOR EACH ROW
BEGIN
INSERT INTO AuditLog(action, table_name, record_id, action_time)
VALUES ('INSERT', 'Appointments', NEW.appointment_id, NOW());
END //
DELIMITER ;

-- BEFORE UPDATE on Billing (auto-populate bill_date)
DELIMITER //
CREATE TRIGGER before_billing_update
BEFORE UPDATE ON Billing
FOR EACH ROW
BEGIN
IF NEW.payment_status = 'Paid' AND OLD.payment_status != 'Paid' THEN
	SET NEW.billing_date = NOW();
END IF;
END //
DELIMITER ;

-- AFTER UPDATE on Appointments (sync VisitHistory)
DELIMITER //
CREATE TRIGGER after_appointment_update
AFTER UPDATE ON Appointments
FOR EACH ROW
BEGIN
IF NEW.status = 'Completed' AND OLD.status != 'Completed' THEN
	INSERT INTO VisitHistory(appointment_id, visit_date)
	VALUES (NEW.appointment_id, NOW());
END IF;
END //
DELIMITER ;

-- BEFORE DELETE on Patients (block deletion if active appointments)
DELIMITER //
CREATE TRIGGER before_patient_delete
BEFORE DELETE ON Patients
FOR EACH ROW
BEGIN
IF EXISTS (SELECT 1 FROM Appointments WHERE patient_id = OLD.patient_id AND status='Scheduled') THEN
	SIGNAL SQLSTATE '45000'
	SET MESSAGE_TEXT = 'Cannot delete a patient with active appointments';
END IF;
END //
DELIMITER ;

-- AFTER DELETE on Patients (archive deleted patients)
CREATE TABLE DeletedPatientsArchive (
patient_id INT,
first_name VARCHAR(50),
last_name VARCHAR(50),
deleted_at TIMESTAMP
);

DELIMITER //
CREATE TRIGGER after_patient_delete
AFTER DELETE ON Patients
FOR EACH ROW
BEGIN
INSERT INTO DeletedPatientsArchive(patient_id, first_name, last_name, deleted_at)
VALUES (OLD.patient_id, OLD.first_name, OLD.last_name, NOW());
END //
DELIMITER ;









