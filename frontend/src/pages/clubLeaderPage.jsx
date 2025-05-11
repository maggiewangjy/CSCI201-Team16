import NavBar from "../components/navBar.jsx";
import Calendar from "../components/Calendar.jsx";
import "../styles/clubLeaderPage.css";
import CreateEvent from "../components/createEvent.jsx"
import EventInfo from "../components/eventInfo.jsx"
import { useState } from "react";
import MemberAttendance from "../components/MemberAttendance.jsx";
import EditEvent from '../components/editEvent.jsx';

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
    const [showSelectedEventDate, setShowSelectedEventDate] = useState(false);

    const openCreateEvent = () => {
        setSelectedEventDate(null);
        setShowSelectedEventDate(false);
        setEditEvent(false);
        setShowCreateEvent(true);
    }

    const openSelectedDate = async (date) => {
        setSelectedEventDate(date);
        setShowSelectedEventDate(true);
        setEditEvent(false);
        setShowCreateEvent(false);
    }

    const openEditEvent = async (eventId) => {
        setEditEventID(eventId);
        setShowSelectedEventDate(false);
        setEditEvent(true);
        setShowCreateEvent(false);
    }

    const closeEditEvent = async () => {
        setEditEventID(null);
        setSelectedEventDate(date);
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
            {showSelectedEventDate && <EventInfo selectedDate={selectedEventDate} onEditEvent={openEditEvent}/>}
            {showCreateEvent && <CreateEvent/>}
            {showEditEvent && <EditEvent eventId={editEventID} onClose={() => setEditEventID(null)} />}
            {/* {(selectedEventDate !== null) && <EventInfo selectedDate={selectedEventDate}/>} */}
       </div>
       <MemberAttendance/>
    </div>
    )
}

export default ClubLeaderPage
