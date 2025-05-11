import { useState } from "react";
import NavBar from "../components/navBar.jsx";
import Calendar from "../components/Calendar.jsx";
import CreateEvent from "../components/createEvent.jsx"
import EventInfo from "../components/eventInfo.jsx"
import MemberAttendance from "../components/MemberAttendance.jsx";
import EditEvent from '../components/editEvent.jsx';
import ViewAttendance from "../components/viewAttendance.jsx";
import "../styles/clubLeaderPage.css";

function ClubLeaderPage(){
    const currDate = new Date();
    const mm = String(currDate.getMonth() + 1).padStart(2, '0');
	const dd = String(currDate.getDate()).padStart(2, '0');
	const yyyy = currDate.getFullYear();
	const mmddyyyy = `${mm}${dd}${yyyy}`;
    const [showCreateEvent, setShowCreateEvent] = useState(false);
    const [selectedEventDate, setSelectedEventDate] = useState(mmddyyyy);
    const [editEventID, setEditEventID] = useState(null);
    const [showEditEvent, setEditEvent] = useState(false);
    const [showViewAttendance, setViewAttendance] = useState({attendeeList: [], eventName: null});
    const [showSelectedEventDate, setShowSelectedEventDate] = useState(false);

    const openCreateEvent = () => {
        setSelectedEventDate(null);
        setShowSelectedEventDate(false);
        setEditEvent(false);
        setShowCreateEvent(true);
        setViewAttendance(null);
    }

    const openSelectedDate = (date) => {
        setSelectedEventDate(date);
        setShowSelectedEventDate(true);
        setEditEvent(false);
        setShowCreateEvent(false);
        setViewAttendance(null);
    }

    const openEditEvent = (eventId) => {
        setEditEventID(eventId);
        setShowSelectedEventDate(false);
        setEditEvent(true);
        setShowCreateEvent(false);
        setViewAttendance(null);
    }

    const openViewAttendance = (eventId, attendees, name) => {
        setEditEventID(eventId);
        setShowSelectedEventDate(false);
        setEditEvent(false);
        setShowCreateEvent(false);
        setViewAttendance({attendeeList: attendees, eventName: name});
    }

    const closeEditEvent = () => {
        setEditEventID(null);
        setSelectedEventDate(selectedEventDate);
        setShowSelectedEventDate(true);
        setEditEvent(false);
        setShowCreateEvent(false);
    }

    return (
    <div>
       <NavBar onCreateEventClick={openCreateEvent}/>
       <div id="calendar-event-component">
            <Calendar selectEventDate={openSelectedDate} currDateSelected={selectedEventDate}/>
            {/* {showCreateEvent && <p>Create Component</p>} */}
            {showSelectedEventDate && <EventInfo selectedDate={selectedEventDate} onEditEvent={openEditEvent} onViewAttendance={openViewAttendance}/>}
            {showCreateEvent && <CreateEvent/>}
            {showEditEvent && <EditEvent eventId={editEventID} onClose={() => setEditEventID(null)} />}
            {showViewAttendance !== null && (<ViewAttendance selectedDate={selectedEventDate} attendees={showViewAttendance.attendeeList} eventName={showViewAttendance.eventName} 
                onClose={() => {setViewAttendance(null); setShowSelectedEventDate(true);}}/>)}            
            {/* {(selectedEventDate !== null) && <EventInfo selectedDate={selectedEventDate}/>} */}
       </div>
       <MemberAttendance/>
    </div>
    )
}

export default ClubLeaderPage
