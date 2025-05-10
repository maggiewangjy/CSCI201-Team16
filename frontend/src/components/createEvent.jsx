import { useEffect } from 'react';
import "../styles/createEvent.css";

function CreateEvent(){
    return(
        <div id="event">
            <div>
            <h1 class="titles"><strong>Create Event</strong></h1>
            <div> 
                <form id="form"> 
                    <div><label>Event Name:</label><br/><input type="text" placeholder="Event Name"/></div>
                    <div><label>Date:</label><br/><input type="date" placeholder="Date"/></div>
                    <div id="times">
                        <div>
                            <label>Start Time: </label><br/><input type="time"/>
                        </div>
                        <div>
                            <label>End Time: </label><br/><input type="time"/>
                        </div>
                    </div>
                    <div><label>Location:</label><br/><input type="text" placeholder="Location"/></div>
                    <div><label>Description:</label><br/><textarea placeholder="Description"/></div>
                </form>
            </div>
            <div id="buttons">
                <button>Create Event</button>
            </div>
            </div>
        </div>
    );
}

export default CreateEvent;