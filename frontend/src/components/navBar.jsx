import { useEffect } from "react";
import { Navigate } from "react-router-dom";
import "../styles/navBar.css";
import logo from "../images/logo.png";

function NavBar({onCreateEventClick}){
	
	const logIn = () => {
		return <Navigate to="/login"/>
	}
	
	const logOut = () => {
		localStorage.clear();
		return <Navigate to="/login"/>
	}
	
	const goToHome = () => {
		return <Navigate to="/clubLeaderPage"/>
	}

	// REVERT THIS PART FROM === to !===
	if (localStorage.getItem("logged-in") === "true"){
		return (
			<div id="header">
				<div id="header-left">
					<img src={logo} alt="Logo" width="50" height="50"/>
				</div> 
				<h1 id="header-mid">CSCI 201 TEAM 16 CLUB</h1> 
				<div id="header-right">
					<button id="logOut-btn" onClick={logIn}>Log in</button>
				</div> 
			</div>
			);
	} else {
		return (
			<div id="header">
				<div id="header-left">
					<img src={logo} alt="Logo" width="50" height="50"/>
					<button id="home-btn" onClick={goToHome}>Home</button>
					<button id="create-btn" onClick={onCreateEventClick}>Create Event</button>
					<a href="#attendance-container">
						<button id="view-btn">View Participation</button>
					</a>
				</div> 
				<div id="header-right">
					<button id="logOut-btn" onClick={logOut}>Log Out</button>
				</div> 
			</div>
			)
	}
}

export default NavBar;