import { useEffect, useState } from 'react';
import { useNavigate } from "react-router-dom";
import "../styles/eventInfo.css";

function EventInfo({ selectedDate, onEditEvent, onViewAttendance }) {
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

    // useEffect(() => {
    //     const userID = localStorage.getItem("userID");
    //     console.log("Current user ID:", userID);
    // }, []);

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
        console.log('Fetching attendees for eventID:', eventID);
        try {
            // Get attendees of event from backend 
            const URL = `http://localhost:8080/Team16_CSCI201_Project/GetAttendeesList?eventID=${encodeURIComponent(eventID)}`;
            const response = await fetch(URL);
            const result = await response.json();

            // If GetEventByDateServlet connection to backend successful
            if(result.status === "success" && result.data && result.data.names) {
                setAttendees(result.data.names);
            }
            else {
                setAttendees([]);
            }
        } catch (err) {
            setError(`Connection Error for Attendees List`);
        }
    };

    const pastEventDate = () => {
        if (!selectedDate) return false;

        const formatDate = (date) => {
            const getMonth = date.substring(0,2);
            const getDay = date.substring(2, 4);
            const getYear = date.substring(4,8);

            return (`${getYear}-${getMonth}-${getDay}T00:00:00`)
        }
        const currentDate = new Date();

        const month = String(currentDate.getMonth() + 1).padStart(2, '0');
        const day = String(currentDate.getDate() + 1).padStart(2, '0');
        const year = currentDate.getFullYear();

        const current = new Date(`${year}-${month}-${day}`); // Current Date
        const selected = new Date(formatDate(selectedDate)); // Selected Date 

        return (selected < current);
    }

    const addAttendee = async () => {
        const name = localStorage.getItem("name");
        const email = localStorage.getItem("email");
        
        console.log('Attempting to add attendee:', { name, email, eventID: events[currentIndex].eventID });

        try {
            const URL = `http://localhost:8080/Team16_CSCI201_Project/AddAttendeeToEvent`;
            const response = await fetch(URL, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    name: name,
                    email: email,
                    eventID: events[currentIndex].eventID
                })
            });
            
            console.log('Response status:', response.status);
            const result = await response.json();
            console.log('Response data:', result.data);

            if (result.status === "success") {
                console.log('Successfully added attendee');
                // Refresh the attendees list
                fetchAttendees(events[currentIndex].eventID);
                setError(null);
            } else {
                console.error('Failed to add attendee:', result);
                setError(result.message || "Failed to add attendee");
            }
        } catch (err) {
            console.error('Error in addAttendee:', err);
            setError("Connection error while adding attendee");
        }
    };

    const removeAttendee = async () => {
        const name = localStorage.getItem("name");
        const email = localStorage.getItem("email");
        
        console.log('Attempting to remove attendee:', { name, email, eventID: events[currentIndex].eventID });

        try {
            const URL = `http://localhost:8080/Team16_CSCI201_Project/RemoveAttendeeFromEvent`;
            const response = await fetch(URL, {
                method: "POST",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({
                    name: name,
                    email: email,
                    eventID: events[currentIndex].eventID
                })
            });
            
            console.log('Response status:', response.status);
            const result = await response.json();
            console.log('Response data:', result.data);

            if (result.status === "success") {
                console.log('Successfully removed attendee');
                // Refresh the attendees list
                fetchAttendees(events[currentIndex].eventID);
                setError(null);
            } else {
                console.error('Failed to remove attendee:', result);
                setError(result.message || "Failed to remove attendee");
            }
        } catch (err) {
            console.error('Error in removeAttendee:', err);
            setError("Connection error while removing attendee");
        }
    }

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
                        <h1 className="titles"><strong>{formatDate(selectedDate)}</strong></h1>
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
                        { pastEventDate() ? (
                            <div id="view-attendance-button">
                                <button onClick={() => onViewAttendance(events[currentIndex].eventID, attendees, events[currentIndex].name)}>View Event Attendance</button>
                            </div>
                        ) : (
                            <div id="edit-attend-buttons">
                                {isLoggedIn && (<button className="bottom-buttons" onClick={() => onEditEvent(events[currentIndex].eventID)}>Edit Event</button>)}
                                {isLoggedIn && !attendees.includes(localStorage.getItem("name")) ? (
                                    <button className="bottom-buttons" onClick={addAttendee}>Attend</button>
                                ) : (
                                    <button className="bottom-buttons" onClick={removeAttendee}>Unattend</button>
                                )}
                            </div>
                        )}
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
                {events.length > 1 ? (
                    <div id="navigation-buttons">
                        <button className="nav-buttons" onClick={prevEvent}>&larr;</button>
                        <button className="nav-buttons" onClick={nextEvent}>&rarr;</button>
                    </div>
                ) : (<div></div>)}
            </div>
    );
}

export default EventInfo;