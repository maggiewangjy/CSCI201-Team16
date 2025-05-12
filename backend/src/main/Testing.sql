USE ClubEventsDB;

INSERT INTO Users (name, email, password)
VALUES ('jack', 'trojan@usc.edu', '123');

INSERT INTO Events (name, startTime, endTime, location, agenda, date, dateMonth)
VALUES (
  'Hackathon 2025',
  '09:00:00',
  '17:00:00',
  'TCC 450',
  'Workshops and Coding',
  '05152025',
  '052025'
);

INSERT INTO Attendance (name, email, eventID)
VALUES ('Carlos Vega', 'trojan@usc.edu', 1);
