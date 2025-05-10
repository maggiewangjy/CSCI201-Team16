import { useEffect, useState } from 'react';
import { useParams, useNavigate } from "react-router-dom";
import "../styles/eventInfo.css";

function EventInfo({ selectedDate }) {
    const [events, setEvents] = useState([]);
    const [attendees, setAttendees] = useState([]);
    const [currentIndex, setCurrentIndex] = useState(0);
    const [error, setError] = useState(null);
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const navigate = useNavigate();

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
            setError(`Connection Error for Fetching Events`);
            setEvents(null);
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
                setAttendees(result.data.names);
            }
            else {
                setAttendees([]);
            }
        } catch (err) {
            setError(`Connection Error for Attendees List`);
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

        // Separate time from am/pm
        const [time, AMPM] = timeString.split(' ');
        // Separate hours from minutes
        const [hours, minutes] = time.split(':');

        // Format variables
        const hour = parseInt(hours);
        const ampm = AMPM.toLowerCase()

        return `${hour}:${minutes}${ampm}`;
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

    const handleDelete = async () => {
        if (!window.confirm("Are you sure you want to delete this event?")) return;

        const eventID = events[currentIndex].eventID;
        try {
            const URL = `http://localhost:8080/Team16_CSCI201_Project/DeleteEvent`;
            const response = await fetch(URL, { 
                method: "POST",
                headers: { "Content-Type": "application/x-www-form-urlencoded" },
                body: `eventID=${encodeURIComponent(eventID)}` 
            });
            const result = await response.json();
            
            if (result.status === "success"){
                navigate("/clubLeaderPage");
            }
            else{
                setError("Failed to delete event.");
            }
        } catch (err) {
            setError("Connection Error: Could not delete event.");
        }
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
                            {isLoggedIn && <button id="delete-button" onClick={handleDelete}>Delete Event</button>}
                        </div>
                        <div id="event-card">
                            <div id="header-info">
                                <h1>{events[currentIndex].name}</h1>
                                <p>{formatTime(events[currentIndex].startTime)} - {formatTime(events[currentIndex].endTime)}</p>
                                {events[currentIndex].location != null ? (<p>Location: {events[currentIndex].location}</p>):(<p></p>)}
                            </div>
                            {events[currentIndex].agenda != null ? (
                                <div>
                                    <h4 className="titles"><strong>Agenda</strong></h4>
                                    <p>{events[currentIndex].agenda}</p>
                                </div>
                            ):(<p></p>)}
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
                            {isLoggedIn && <button className="bottom-buttons">Edit Event</button>}
                            {isLoggedIn && <button className="bottom-buttons">Attend</button>}
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
                        <button className="nav-buttons" onClick={prevEvent}>&larr;</button>
                        <button className="nav-buttons" onClick={nextEvent}>&rarr;</button>
                    </div>
                ) : (<div></div>)}
            </div>
    );
}

export default EventInfo;