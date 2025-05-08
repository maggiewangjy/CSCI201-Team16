import { useEffect, useState } from 'react';
import "../styles/eventInfo.css";

function EventInfo({ selectedDate }) {
    const [event, setEvent] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        // Check login state
        setIsLoggedIn(localStorage.getItem("logged-in") === "true");
        if (selectedDate) {
            fetchEvent();
        }
    }, [selectedDate]);

    const fetchEvent = async () => {
        try {
            setLoading(true);

            const URL = 'http://localhost:8080/Team16_CSCI201_Project/GetEventByDate?date=selectedDate';
            const response = await fetch(URL);
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const result = await response.json();
            
            // Format the selected date to match the backend date format (YYYY-MM-DD)
            const formattedDate = new Date(selectedDate).toISOString().split('T')[0];
            console.log('Looking for events on date:', formattedDate);
            
            // Find the event for the selected date
            const eventForDate = events.find(event => {
                const eventDate = new Date(event.date).toISOString().split('T')[0];
                return eventDate === formattedDate;
            });
            
            console.log('Found event:', eventForDate);
            setEvent(eventForDate || null);
            setError(null);
        } catch (err) {
            console.error('Error fetching events:', err);
            setError(`Connection Error: Could not connect to the backend server at http://localhost:8080. Please ensure:
            1. The backend server is running
            2. The server is running on port 8080
            3. The application is deployed with context path 'CSCI201-Team16'`);
            setEvent(null);
        } finally {
            setLoading(false);
        }
    };

    const formatTime = (timeString) => {
        if (!timeString) return '';
        // Extract hours and minutes from the time string
        const [hours, minutes] = timeString.split(':');
        const hour = parseInt(hours);
        const ampm = hour >= 12 ? 'pm' : 'am';
        const formattedHour = hour % 12 || 12; // Convert 0 to 12 for 12am
        return `${formattedHour}:${minutes}${ampm}`;
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

    const formatDate = (date) => {
        const dateObj = new Date(date);
        const month = dateObj.toLocaleString('default', { month: 'long' });
        const day = dateObj.getDate();
        const year = dateObj.getFullYear();
        const suffix = getOrdinalSuffix(day);
        return `${month} ${day}${suffix}, ${year}`;
    };

    if (!selectedDate) {
        return null;
    }

    if (loading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div className="error-message">{error}</div>;
    }

    return (
        <div id="event">
            <div>
                <h1 className="titles"><strong>{formatDate(selectedDate)}</strong></h1>
                {event ? (
                    <div id="event-card">
                        <h1>{event.name}</h1>
                        <p>{formatTime(event.time)}</p>
                        <br/>
                        <h4 className="titles"><strong>Overview</strong></h4>
                        <p>{event.notes}</p>
                        <h4 className="titles"><strong>Attendees</strong></h4>
                        <ul>
                            {/* TODO: Add attendees list when backend endpoint is available */}
                            <li>Loading attendees...</li>
                        </ul>
                    </div>
                ) : (
                    <div id="event-card">
                        <p>No events scheduled for this date.</p>
                    </div>
                )}
                <div id="buttons">
                    {isLoggedIn && <button>Edit Event</button>}
                    {isLoggedIn && <button>Attend</button>}
                </div>
            </div>
        </div>
    );
}

export default EventInfo;