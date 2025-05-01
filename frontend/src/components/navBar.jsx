import { useEffect } from "react";
import "../styles/navBar.css";
import logo from "../images/logo.png";

function NavBar(){
	const logOut = () => {
		localStorage.clear();
		return <Navigate to="/login"/>
	}
	
	if (localStorage.getItem("logged-in") !== "true"){
		return (
			<div id="header">
				<div id="header-left">
					<img src={logo} alt="Logo" width="50" height="50"/>
				</div> 
				<h1 id="header-mid">CSCI 201 TEAM 16 CLUB</h1> 
				<div id="header-right">
					<button id="logOut-btn" onclick="logIn()">Log in</button>
				</div> 
			</div>
			);
	} else {
		return (
			<div id="header">
				<div id="header-left">
					<img src={logo} alt="Logo" width="50" height="50"/>
					<button id="home-btn" onclick="goToHome()">Home</button>
					<button id="create-btn" onclick="openEventComponent()">Create Event</button>
					<button id="view-btn" onclick="goToParticipation()">View Participation</button>
				</div> 
				<div id="header-right">
					<button id="logOut-btn" onclick={logOut()}>Log Out</button>
				</div> 
			</div>
			)
	}
}

export default NavBar;