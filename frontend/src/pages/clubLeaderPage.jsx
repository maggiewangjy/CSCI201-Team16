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
    const mm = String(currDate.getMonth() + 1).padStart(2, '0');
	const dd = String(currDate.getDate()).padStart(2, '0');
	const yyyy = currDate.getFullYear();
	const mmddyyyy = `${mm}${dd}${yyyy}`;
    const [showCreateEvent, setCreateEvent] = useState(false);

    const [selectedEventDate, setSelectedEventDate] = useState(mmddyyyy);


    const openCreateEvent = () => {

        setSelectedEventDate(null);
        setCreateEvent(true);
    }

    const openSelectedDate = async (date) => {
        setCreateEvent(false);

        setSelectedEventDate(date);
        

    }

    return (
    <div>
       <NavBar onCreateEventClick={openCreateEvent}/>
       <div id="calendar-event-component">
            <Calendar selectEventDate={openSelectedDate} currDateSelected={selectedEventDate}/>
            {showCreateEvent && <p>Create Component</p>}
            {/* {selectedEventDate && <EventInfo selectedDate={selectedEventDate}/>} */}
            {/* {showCreateEvent && <CreateEvent/>}
            {(selectedEventDate !== null) && <EventInfo selectedDate={selectedEventDate}/>} */}
       </div>
       <MemberAttendance/>
    </div>
    )
}

export default ClubLeaderPage
