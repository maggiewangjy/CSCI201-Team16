import { useEffect, useState } from 'react';
import "../styles/eventInfo.css";

function EventInfo({ selectedDate }) {
    const [events, setEvents] = useState([]);
    const [attendees, setAttendees] = useState([]);
    const [currentIndex, setCurrentIndex] = useState(0);
    const [error, setError] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        // Check login state
        setIsLoggedIn(localStorage.getItem("logged-in") === "true");
        if (selectedDate) {
            fetchEvent();
        }
    }, [selectedDate]);

    useEffect(() => {
        if (events.length > 0) {
            fetchAttendees(events[currentIndex].eventID);
        }
    }, [currentIndex, events]);

    const fetchEvent = async () => {
        try {
            // Get event from backend 
            const URL = `http://localhost:8080/Team16_CSCI201_Project/GetEventByDate?date=${encodeURIComponent(selectedDate)}`;
            const response = await fetch(URL);
            const result = await response.json();

            // If GetEventByDateServlet connection to backend successful
            if(result.status === "success" ) {
                // If no events are found on this date, event card will display "No Event"  
                if(result.message === "No events found."){
                    setEvents([]);
                    setCurrentIndex(0);

                }
                else if(result.message === "Events retrieved.") {
                    setEvents(result.data)
                    setCurrentIndex(0);
                }
            }
        } catch (err) {
            console.error('Error fetching events:', err);
            setError(`Connection Error: Could not connect to the backend server at http://localhost:8080. Please ensure:
            1. The backend server is running
            2. The server is running on port 8080
            3. The application is deployed with context path 'CSCI201-Team16'`);
            setEvent(null);
        } finally {
            
        }
    };

    const fetchAttendees = async (eventID) => {
        try {
            // Get attendees of event from backend 
            const URL = `http://localhost:8080/Team16_CSCI201_Project/GetAttendeesList?eventID=${encodeURIComponent(eventID)}`;
            const response = await fetch(URL);
            const result = await response.json();

            // If GetEventByDateServlet connection to backend successful
            if(result.status === "success" && result.data.names) {
                console.log('In if: ' + result.message)
                setAttendees(result.data.names);
            }
            else {
                setAttendees([]);
            }
        } catch (err) {
            console.error('Error fetching events:', err);
            setError(`Connection Error for Attendees: Could not connect to the backend server at http://localhost:8080. Please ensure:
            1. The backend server is running
            2. The server is running on port 8080
            3. The application is deployed with context path 'CSCI201-Team16'`);
        } finally {
            
        }
    };

    const nextEvent = () => {
        setCurrentIndex((prev) => (prev + 1) % events.length);
    };

    const prevEvent = () => {
        setCurrentIndex((prev) => (prev - 1 + events.length) % events.length);
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

    if (!selectedDate) {
        return null;
    }

    if (error) {
        return <div className="error-message">{error}</div>;
    }

    return (
            <div>
                {events.length > 0 ? (
                    <div> 
                        <div id="heading">
                            <h1 className="titles"><strong>{formatDate(selectedDate)}</strong></h1>
                            {isLoggedIn && <button id="delete-button">Delete Event</button>}
                        </div>
                        <div id="event-card">
                            <div id="header-info">
                                <h1>{events[currentIndex].name}</h1>
                                <p>{formatTime(events[currentIndex].startTime)} - {formatTime(events[currentIndex].endTime)}</p>
                                <p>Location: {events[currentIndex].location}</p>
                            </div>
                            <h4 className="titles"><strong>Agenda</strong></h4>
                            <p>{events[currentIndex].agenda}</p>
                            <h4 className="titles"><strong>Attendees</strong></h4>
                            <ul>
                                {attendees.length > 0 ? (
                                    attendees.map((name, index) => <li key={index}>{name}</li>)
                                ) : (
                                    <li>No attendees</li>
                                )}
                            </ul>
                        </div>
                        <div id="buttons">
                            {isLoggedIn && <button class="bottom-buttons">Edit Event</button>}
                            {isLoggedIn && <button class="bottom-buttons">Attend</button>}
                        </div>
                    </div>
                ) : (
                    <div>
                        <h1 className="titles"><strong>{formatDate(selectedDate)}</strong></h1>
                        <div id="no-event">
                            <div id="box">
                                <p>No Event Scheduled</p>
                            </div>
                        </div>
                    </div>
                )}
                {events.length > 0 ? (
                    <div id="navigation-buttons">
                        <button class="nav-buttons" onClick={prevEvent}>&larr;</button>
                        <button class="nav-buttons" onClick={nextEvent}>&rarr;</button>
                    </div>
                ) : (<div></div>)}
            </div>
    );
}

export default EventInfo;