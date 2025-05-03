import NavBar from "../components/navBar.jsx";
import Calendar from "../components/Calendar.jsx";
import EventInfo from "../components/eventInfo.jsx";
import "../styles/clubLeaderPage.css";
// import CreateEvent from "" //IMPORT EVENT COMPONENT
// import SelectedEvent from "" //IMPORT SELECTED COMPONENT
import { useState } from "react";
import MemberAttendance from "../components/MemberAttendance.jsx";

function ClubLeaderPage(){
    const currDate = new Date();
    const [showCreateEvent, setCreateEvent] = useState(false);
    const [selectedEventDate, setSelectedEventDate] = useState(currDate);

    const openCreateEvent = () => {
        console.log("openCreateEvent clicked");
        setSelectedEventDate(null);
        setCreateEvent(true);
    }

    const openSelectedDate = async (date) => {
        setCreateEvent(false);
        const newDate = new Date(currDate);
        newDate.setDate(date);
        setSelectedEventDate(newDate);
    }

    return (
    <div>
       <NavBar onCreateEventClick={openCreateEvent}/>
       <div id="calendar-event-component">
            <Calendar selectEventDate={openSelectedDate} currDateSelected={selectedEventDate?.getDate()}/>
            {showCreateEvent && <p>Create Component</p>}
            {selectedEventDate && <EventInfo selectedDate={selectedEventDate}/>}
            {/* {showCreateEvent && <CreateEvent/>}
            {(selectedEventDate !== null) && <selectedEventDate eventDate={selectedEventDate}/>} */}
       </div>
       <MemberAttendance/>
    </div>
    )
}

export default ClubLeaderPage