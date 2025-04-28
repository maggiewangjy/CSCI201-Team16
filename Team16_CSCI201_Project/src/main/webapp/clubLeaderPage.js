const user_email = localStorage.getItem("user_email");
var baseURL = "http://localhost:8080/Team16_CSCI201_Project/"

// On pageLoad load all the information from the calender, 
// and the participation of all events

function createEvent() {
	// This function is associated with create event btn
	// To Do: 
	// - Open the event component as shown in figma diagram
	// - Create a blank fetch to a servlet with base url 
	//	and the servlet send information of the event
}
function goToParticipation() {
	window.location.href("#participation-container");
}

function logOut() {
	localStorage.clear();
	window.location.href("index.html");
}