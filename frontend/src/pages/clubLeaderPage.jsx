import NavBar from "./components/navBar.jsx";
import EventInfo from "./components/eventInfo.jsx";
import CreateEvent from "../components/createEvent.jsx";
import EditEvent from "../components/editEvent.jsx";

function clubLeaderPage(){
    return (
    <div>
        <NavBar/>
        {/* <EventInfo/> */}
        {/* <CreateEvent/> */}
        <EditEvent/>
    </div>
    )
}
export default clubLeaderPage