import React, { useState, useEffect } from "react";
import "../styles/viewAttendance.css";

function ViewAttendance ({ selectedDate, attendees, eventName, onClose }) {
    const [userCount, setUserCount] = useState(0);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchUserCount = async () => {
            try {
                setLoading(true);
                const URL = `http://localhost:8080/Team16_CSCI201_Project/GetUserCount`;
                const response = await fetch(URL);
                const result = await response.json();

                if (result.status === "success" && result.data && result.data.totalUsers !== undefined) {
                    setUserCount(result.data.totalUsers);
                    setError(null);
                } else {
                    setUserCount(0);
                    setError("Failed to fetch total user count: Invalid response format");
                }
            } catch (err) {
                console.error("Connection Error for Attendees List:", err);
                setError(`Connection error while fetching user count: ${err.message}`);
                setUserCount(0);
            } finally {
                setLoading(false);
            }
        };

        fetchUserCount();
    }, []);

    const getAttendancePercentage = () => {
        if (!userCount || !attendees) return 0;
        console.log("in percentage");
        console.log("userCount: " + userCount);
        console.log("attendees.length: " + attendees.length);
        return ((attendees.length / userCount) * 100).toFixed(1);
    };

    const getOrdinalSuffix = (day) => {
        if (day > 3 && day < 21) return 'th';
        switch (day % 10) {
            case 1: return 'st';
            case 2: return 'nd';
            case 3: return 'rd';
            default: return 'th';
        }
    };

    const formatDate = (selectedDate) => {
        // Separate month, day, and year 
        const numMonth = parseInt(selectedDate.substring(0, 2)); 
        const day = parseInt(selectedDate.substring(2, 4));
        const year = parseInt(selectedDate.substring(4, 8));
    
        // Get month string and day suffix 
        const monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
        const realMonth = monthNames[numMonth - 1];
        const suffix = getOrdinalSuffix(day);

        // Return formatted date
        return `${realMonth} ${day}${suffix}, ${year}`;
    };

    return (
        <div>
            <h1 className="titles"><strong>{formatDate(selectedDate)}</strong></h1>
            <div id="view-attendance-card">
                <h1>{eventName}</h1>
                <h2>Event Attendance</h2>
                { error ? (
                    <div className="error-message">{error}</div>
                ) : (
                    <div className="attendance-details">
                        <div id="percentage">
                            <p>{getAttendancePercentage()}%</p>
                        </div>
                        <h4 className="titles"><strong>Attendees</strong></h4>
                        <ul>
                            {attendees.length > 0 ? (
                                attendees.map((name, index) => <li key={index}>{name}</li>)
                            ) : (
                                <li>No attendees</li>
                            )}
                        </ul>
                    </div>
                )} 
            </div>
            <div id="back-button">
                <button onClick={onClose}>&larr; Back to Event</button>
            </div>
        </div>
    );
}

export default ViewAttendance;