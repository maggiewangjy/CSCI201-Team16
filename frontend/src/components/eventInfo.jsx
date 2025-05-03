import { useEffect } from 'react';
import "../styles/eventInfo.css";

function EventInfo(){
    // useEffect(() => {
    //     const fetchEvent = async() => {
    //         try{
    //             const response = await fetch('API-Endpoint');
    //             if(!response.ok){
    //                 throw new Error()
    //             } 
    //             const json = await response.json();
    //         }
    //         catch{

    //         }
    //     }
    // })
    
    // const attendEvent = () => {

    // }

    // const editEvent = () => {

    // }

    // const viewAttendance = () => {

    // } 

    // if(localStorage.getItem("logged-in") !== "true"){
    //     // const today = new Date();

    //     // const monthList = ["January","February","March","April","May","June","July","August","September","October","November","December"];

    //     // const day = today.getDate().toString();
    //     // const month = monthList[today.getMonth() + 1];
    //     // const year = today.getFullYear().toString();

    //     // const currentDate = `${month} ${day}, ${year}`;
    //     // const queryDate = `${today.getFullYear()}-${(today.getMonth() + 1).toString().padStart(2, '0')}-${today.getDate().toString().padStart(2, '0')}`;
        

    // }
    // else{
        return(
            <div id="event">
                <div id="calendar">
                    <p>Calendar</p>
                </div>
                <div>
                <h1 class="titles"><strong>April 16th, 2025</strong></h1>
                <div id="event-card"> 
                    <h1>Club Event #3</h1>
                    <p>8:00am - 12:00pm</p>
                    <br/>
                    <h4 class="titles"><strong>Overview</strong></h4>
                    <p>      
                        Hey everyone! Just a reminder that our second club event is happening this Saturday from 
                        8am to 12pm on McCarthy Quad. We’ll be hosting a community clean-up followed by a light 
                        brunch and time to hang out with fellow members. All supplies will be provided—just bring 
                        yourself and some good energy! Looking forward to seeing you all there and making a 
                        positive impact together.
                    </p>
                    <h4 class="titles"><strong>Attendees</strong></h4>
                    <ul>
                        <li>Grace Shaha</li>
                        <li>Maggie Wang</li>
                        <li>Charlie Vega</li>
                        <li>Grace Shaha</li>
                        <li>Maggie Wang</li>
                        <li>Charlie Vega</li>
                        <li>Grace Shaha</li>
                        <li>Maggie Wang</li>
                        <li>Charlie Vega</li>
                    </ul>
                </div>
                <div id="buttons">
                    <button>Edit Event</button>
                    <button>Attend</button>
                </div>
                </div>
            </div>
        );
    //}
}

export default EventInfo;