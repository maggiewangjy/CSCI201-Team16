import { useEffect, useState } from "react";
import "../styles/memberAttendance.css";
import SearchIcon from "../images/search_icon.png"

function MemberAttendance() {
    useEffect(() => {
        const form = document.getElementById("attendance-search");

        if (form){
            form.addEventListener("submit", async function (e) {
                e.preventDefault();
                const email = e.target.email.value;
                const URL = `http://localhost:8080/Team16_CSCI201_Project/GetAttendanceByMember?email=${encodeURIComponent(email)}`;
                const response = await fetch(URL);
                const result = await response.json();
        
                if (result.status === "success"){
                    document.getElementById("search-result").innerHTML = <h3>{result.data.percentage} %</h3>
                } else if (result.status === "fail"){
                    document.getElementById("search-result").innerHTML = <h3>N/A</h3>
                } else {
                    console.log(result.message);
                    alert(result.message);
                }
            })
        }
    })

    return (
        <div id="attendance-container">
            <div id="attendance-title-container">
                <h2 id="attendance-title">Attendace by Member</h2>
            </div>
            <form id="attendance-search" action="AttendanceSearch"> 
                <input src={SearchIcon} id="searchButton" type="image" alt="search Button"></input>
                <input id="search" type="search" name="email" placeholder="Type in your email trojan@usc.edu" required></input>
            </form>
            <div id="search-result">
            </div>
        </div>
    )
}
export default MemberAttendance;
