import NavBar from "../components/navBar.jsx";
import Calendar from "../components/Calendar.jsx";
import "../styles/clubLeaderPage.css";
import CreateEvent from "../components/createEvent.jsx"
import SelectedEvent from "../components/eventInfo.jsx"
import { useState } from "react";

function GuestPage(){
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
            {showCreateEvent && <CreateEvent/>}
            {(selectedEventDate !== null) && <SelectedEvent eventDate={selectedEventDate}/>}
       </div>
    </div>
    )
}
export default GuestPage;