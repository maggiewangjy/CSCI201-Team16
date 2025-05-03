import { useEffect, useState } from "react";
import "../styles/memberAttendance.css";
import SearchIcon from "../images/search_icon.png";

function MemberAttendance() {
    const handleSearch = async () => {
        try {
            const response = await fetch('/memberAttendance', {
                method: 'GET'
              });
            console.log('Response status:', response.status);
            const data = await response.json();
            console.log('Response data:', data);
            document.getElementById('search-result').innerHTML = `
                <h1>${data.attendance}</h1>
            `;
        } catch (error) {
          console.error('Error: ', error);
          alert('An error occurred getting attendance. Please try again.');
        }
    };

    return (
        <div id="attendance-container">
            <div id="attendance-title-container">
                <h2 id="attendance-title">Attendace by Member</h2>
            </div>
            <form id="attendance-search" action="AttendanceSearch"> 
                <input id="searchButton" type="image" alt="search Button" src={SearchIcon} onClick={handleSearch}/>
                <input id="search" type="search" name="email" placeholder="Type in your email trojan@usc.edu" required></input>
            </form>
            <div id="search-result">
            </div>
        </div>
    )
}
export default MemberAttendance;
