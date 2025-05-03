DROP DATABASE IF EXISTS ClubEventsDB;
CREATE DATABASE ClubEventsDB;
USE ClubEventsDB;
-- Create Users table --
CREATE TABLE Users (
    userID INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('admin', 'member') NOT NULL,
    securityQuestion1 VARCHAR(255),
    securityAnswer1 VARCHAR(255),
    securityQuestion2 VARCHAR(255),
    securityAnswer2 VARCHAR(255)
);

-- Create Events table --
    eventID INT PRIMARY KEY AUTO_INCREMENT,
    createdBy INT,
    name VARCHAR(255) NOT NULL,
    startTime DATETIME NOT NULL,
    endTime DATETIME NOT NULL,
    location VARCHAR(255),
    agenda VARCHAR(255),
    notes VARCHAR(255),
    documents VARCHAR(255),
    FOREIGN KEY (createdBy) REFERENCES Users(userID)
);

-- Create Participants table --
CREATE TABLE Participants (
    participantID INT PRIMARY KEY AUTO_INCREMENT,
    userID INT,
    name VARCHAR(255),
    email VARCHAR(255),
    status ENUM('confirmed', 'pending', 'cancelled'),
    eventID INT,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (userID) REFERENCES Users(userID),
    FOREIGN KEY (eventID) REFERENCES Events(eventID)
);

-- Create Agenda table --
CREATE TABLE Agenda (
    agendaID INT PRIMARY KEY AUTO_INCREMENT,
    eventID INT,
    task VARCHAR(255),
    completion BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (eventID) REFERENCES Events(eventID)
);