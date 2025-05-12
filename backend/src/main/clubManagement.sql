DROP DATABASE IF EXISTS ClubEventsDB;
CREATE DATABASE ClubEventsDB;
USE ClubEventsDB;
-- Create Users table --
CREATE TABLE Users (
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) PRIMARY KEY NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- Create Events table --
CREATE TABLE Events (
    eventID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    startTime TIME NOT NULL,
    endTime TIME NOT NULL,
    location VARCHAR(255),
    agenda TEXT,
    date VARCHAR(255) NOT NULL,
    dateMonth VARCHAR(255) NOT NULL
);

-- Create Attendance table --
CREATE TABLE Attendance (
    attendanceID INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    eventID INT NOT NULL,
    FOREIGN KEY (email) REFERENCES Users(email),
    FOREIGN KEY (eventID) REFERENCES Events(eventID)
);