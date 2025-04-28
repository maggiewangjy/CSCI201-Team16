const user_email = localStorage.getItem("user_email");
var baseURL = "http://localhost:8080/Team16_CSCI201_Project/"

// On pageLoad load all the information from the calender, 
// and the participation of all events

function goToHome() {
	console.log("Go to Home btn Clicked");
	window.location.href = "clubLeaderPage.html";
}

function openEventComponent() {
	// This function is associated with create event btn
	// To Do (Charlie): 
	// - Open the event component as shown in figma diagram
	// - Create a blank fetch to a servlet with base url 
	//	and the servlet send information of the event
	console.log("Create Event btn Clicked");
}
function goToParticipation() {
	console.log("Go to Participation btn Clicked");
	window.location.href = "#participation-container";
}

function logOut() {
	console.log("Log out btn Clicked");
	localStorage.clear();
	window.location.href = "index.html";
}