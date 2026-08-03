-- Day 2 - Assignment
USE health_clinic_db;

-- 1. Creating rooms and doctor_room tables
CREATE TABLE rooms (
room_id INT AUTO_INCREMENT PRIMARY KEY,
room_number VARCHAR(20) UNIQUE NOT NULL,
floor INT,
room_type VARCHAR(50)
);

CREATE TABLE doctor_rooms (
doctor_id INT NOT NULL,
room_id INT NOT NULL,
PRIMARY KEY (doctor_id, room_id),
FOREIGN KEY (doctor_id) REFERENCES doctors(doctor_id) ON DELETE CASCADE,
FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE
);

INSERT INTO rooms(room_number,floor,room_type)
VALUES
('A1',1,'General'),
('B2',2,'Cardiology'),
('C3',3,'Emergency');

INSERT INTO doctor_rooms
VALUES
(1,1),
(1,2);


-- 2. EXPLAIN on at least 3 different queries against the appointments table one with no index, one using a single-column index, one using the composite index
EXPLAIN SELECT * FROM appointments WHERE status = 'Scheduled';
EXPLAIN SELECT * FROM appointments WHERE patient_id = 1;
EXPLAIN SELECT * FROM appointments WHERE doctor_id = 1 AND appointment_date = '2026-08-05 10:00:00';


-- 3. the patient_phones design and verify it satisfies 1NF, 2NF, and 3NF
-- 1NF: Each row holds a single phone number, there is no multi-valued attributes.
-- 2NF: No partial dependency, phone_id is the PK, and patient_id is a foreign key. Phone_number depends entirely on phone_id.
-- 3NF: No transitive dependency, phone_number doesn’t depend on patient attributes like name/email. Only on phone_id. 


-- 4. a covering index for a query that reports doctor_id, appointment_date, status from the appointments table, and 
-- verify with EXPLAIN that Extra shows Using index.
-- this is a covering index
CREATE INDEX idx_doctor_appointments 
ON appointments (doctor_id, appointment_date, status);

EXPLAIN SELECT doctor_id, appointment_date, status 
FROM appointments WHERE doctor_id = 1;



