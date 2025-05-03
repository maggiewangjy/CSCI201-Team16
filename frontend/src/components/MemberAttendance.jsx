import { useEffect, useState } from "react";
import "../styles/memberAttendance.css";

function MemberAttendance() {
    return (
        <div id="attendance-container">
            <div id="attendance-title-container">
                <h2 id="attendance-title">Attendace by Member</h2>
            </div>
            <form id="attendance-search" action="AttendanceSearch"> 
                <input id="searchButton" type="image" alt="search Button" width="75" height="75"></input>
                <input id="search" type="search" name="email" placeholder="Type in your email trojan@usc.edu" required></input>
            </form>
            <div id="search-result">
            </div>
        </div>
    )
}
export default MemberAttendance;
