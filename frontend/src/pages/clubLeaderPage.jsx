import NavBar from "../components/navBar.jsx";
import Calendar from "../components/Calendar.jsx";
import "../styles/clubLeaderPage.css";
// import CreateEvent from "" //IMPORT EVENT COMPONENT
// import SelectedEvent from "" //IMPORT SELECTED COMPONENT
import { useState } from "react";
import MemberAttendance from "../components/MemberAttendance.jsx";
function ClubLeaderPage(){
    const currDate = new Date();
    const [showCreateEvent, setCreateEvent] = useState(false);
    const [selectedEventDate, setSelectedEventDate] = useState(currDate.getDate());


    const openCreateEvent = () => {
        console.log("openCreateEvent cicked");
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
            {(selectedEventDate !== null) && <p>Selected Component {selectedEventDate}</p>}
            {/* {showCreateEvent && <CreateEvent/>}
            {(selectedEventDate !== null) && <selectedEventDate eventDate={selectedEventDate}/>} */}
       </div>
       <MemberAttendance/>
    </div>
    )
}
export default ClubLeaderPage