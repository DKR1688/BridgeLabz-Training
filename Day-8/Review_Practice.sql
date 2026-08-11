create database gla_db;
use  gla_db;

Create Table student (
student_id INT PRIMARY KEY auto_increment,
roll_number VARCHAR(20) UNIQUE NOT Null,
first_name VARCHAR (50) NOT NULL,
last_name VARCHAR(50),
gender VARCHAR(10),
email VARCHAR(50) UNIQUE,
address VARCHAR(100) ,
city VARCHAR(50),
state VARCHAR(50),
course VARCHAR (100),
department VARCHAR(100),
admission_year Year,
created_at Timestamp
);

desc student;

INSERT INTO student (roll_number, first_name, last_name, gender, email, address, city, state, course, department, admission_year, created_at)
VALUES ('2215000546', 'Deepak', 'Rajput', 'Male', 'deppak@gmail.com', 'Satwas', 'Kaman', 'Rajasthan', 'B.Tech',
 'Computer Science Engineering', 2026, CURRENT_TIMESTAMP);
 SELECT * FROM student;
 
create table faculty (
faculty_id int primary key auto_increment,
first_name varchar(50) not null,
last_name varchar(50) not null,
gender varchar(10) not null,
email varchar(50) unique,
address varchar(100),
city varchar(20),
state varchar(20),
department varchar(50),
designation varchar(100),
joining_year year,
created_at timestamp default current_timestamp
);
desc faculty;

insert into faculty (faculty_id, first_name, last_name, gender, email, address, city, state, department, designation, joining_year)
values ('101', 'bruce', 'banner', 'Male', 'bruce@gla.ac.in', 'lucknow UP', 'lucknow', 'UP', 'CS', 'Professor', 2024);
select * from faculty;


INSERT INTO faculty
(faculty_id, first_name, last_name, gender, email, address, city, state, department, designation, joining_year)
VALUES
(102, 'Tony', 'Stark', 'Male', 'tony@gla.ac.in', 'Noida Sector 62', 'Noida', 'UP', 'CSE', 'Associate Professor', 2022),
(103, 'Steve', 'Rogers', 'Male', 'steve@gla.ac.in', 'Mathura Road', 'Mathura', 'UP', 'CSE', 'Assistant Professor', 2023),
(104, 'Natasha', 'Romanoff', 'Female', 'natasha@gla.ac.in', 'Agra Cantt', 'Agra', 'UP', 'IT', 'Assistant Professor', 2021),
(105, 'Wanda', 'Maximoff', 'Female', 'wanda@gla.ac.in', 'Aliganj', 'Lucknow', 'UP', 'ECE', 'Professor', 2020);
SELECT * FROM faculty;


ALTER TABLE faculty
ADD COLUMN salary DECIMAL(10,2);

UPDATE faculty
SET salary = 85000
WHERE faculty_id = 101;

UPDATE faculty
SET salary = 75000
WHERE faculty_id = 102;

UPDATE faculty
SET salary = 65000
WHERE faculty_id = 103;

UPDATE faculty
SET salary = 70000
WHERE faculty_id = 104;

UPDATE faculty
SET salary = 90000
WHERE faculty_id = 105;
SELECT * FROM faculty;


SELECT department, MAX(salary) AS maximum_salary
FROM faculty
GROUP BY department;




