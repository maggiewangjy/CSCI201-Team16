import { useEffect, useState } from "react";
import "../styles/calendar.css";

const currDate = new Date();
const actualDate = new Date();

function Calendar({selectEventDate}) {
	const [dates, setDates] = useState([]);
	const [monthYear, setMonthYear] = useState("");
	const [events, setEvents] = useState(null);
	const [eventFastLookUp, setEventFastLookUp] = useState(new Map());

	useEffect(() => {
		calendar();
	}, []);

	const clickedPrevButton = async () => {
		currDate.setMonth(currDate.getMonth() - 1);
		const activeDate = document.querySelector(".date.active");
		const mm = String(currDate.getMonth() + 1).padStart(2, '0');
		const dd = String(currDate.getDate()).padStart(2, '0');
		const yyyy = currDate.getFullYear();
		const currDatemmyyyy = `${mm}${yyyy}`;
		const actualmm = String(actualDate.getMonth() + 1).padStart(2, '0');
		const actualyyyy = actualDate.getFullYear();
		const actualmmyyyy = `${actualmm}${actualyyyy}`;
		if (activeDate !== null && activeDate.id !== null) {
			document.getElementById(activeDate.id).classList.remove("active");
		}

		await calendar();
		if (currDatemmyyyy === actualmmyyyy){
			selectEventDate(`${actualmm}${dd}${actualyyyy}`);
			manageSelectDate(`${actualmm}${dd}${actualyyyy}`);
		} else {
			selectEventDate(`${mm}01${yyyy}`);
			manageSelectDate(`${mm}01${yyyy}`);
		}
	}

	const clickedNextButton = async () => {
		currDate.setMonth(currDate.getMonth() + 1);
		const activeDate = document.querySelector(".date.active");
		const mm = String(currDate.getMonth() + 1).padStart(2, '0');
		const dd = String(currDate.getDate()).padStart(2, '0');
		const yyyy = currDate.getFullYear();
		const currDatemmyyyy = `${mm}${yyyy}`;
		const actualmm = String(actualDate.getMonth() + 1).padStart(2, '0');
		const actualyyyy = actualDate.getFullYear();
		const actualmmyyyy = `${actualmm}${actualyyyy}`;
		
		if (activeDate !== null && activeDate.id !== null) {
			document.getElementById(activeDate.id).classList.remove("active");
		}

		await calendar();
		if (currDatemmyyyy === actualmmyyyy){
			selectEventDate(`${actualmm}${dd}${actualyyyy}`);
			manageSelectDate(`${actualmm}${dd}${actualyyyy}`);
		} else {
			selectEventDate(`${mm}01${yyyy}`);
			manageSelectDate(`${mm}01${yyyy}`);
		}
	}

	const manageSelectDate = (selectedDate) => {
		selectEventDate(selectedDate);
		const activeDate = document.querySelector(".date.active");
		if (activeDate !== null && activeDate.id !== null) {
			document.getElementById(activeDate.id).classList.remove("active");
		}
		console.log(selectedDate);
		document.getElementById(selectedDate).classList.add("active");
	}

	const calendar = async () => {
		const currYear = currDate.getFullYear();
		const currMonth = currDate.getMonth();
		const mm = String(currDate.getMonth() + 1).padStart(2, '0');
		const firstDay = new Date(currYear, currMonth, 1);
		const lastDay = new Date(currYear, currMonth + 1, 0);
		const mmyyyy = `${mm}${currYear}`
		setMonthYear(currDate.toLocaleString("default", {month: "long", year: "numeric"}));

		const URL = `http://localhost:8080/Team16_CSCI201_Project/GetEventByMonth?dateMonth=${encodeURIComponent(mmyyyy)}`
		const response = await fetch(URL);
		const result = await response.json();
		console.log(result);
		if (result.status === "success"){
			const eventMap = new Map();
			result.data.forEach(event => {
				console.log(event.date);
				eventMap.set(event.date, "nothing");
			})
			console.log(eventMap);
			setEventFastLookUp(eventMap);
			setEvents(result.data);
			console.log(eventFastLookUp);
			console.log(events);
		} else {
			alert(result.message);
		}

		const allDates =[];
		for(let i = firstDay.getDay(); i > 1; i--){
			const prevDate = new Date(currYear, currMonth, 0 - i + 1);
			const mm = String(prevDate.getMonth() + 1).padStart(2, '0');
			const dd = String(prevDate.getDate()).padStart(2, '0');
			const yyyy = prevDate.getFullYear();
			const mmddyyyy = `${mm}${dd}${yyyy}`;

			allDates.push(<div id={mmddyyyy} key={mmddyyyy} className="date inactive">{dd}</div>);
		}
		for(let i = 1; i <= lastDay.getDate(); i++){
			const date = new Date(currYear, currMonth, i);
			const mm = String(date.getMonth() + 1).padStart(2, '0');
			const dd = String(date.getDate()).padStart(2, '0');
			const yyyy = date.getFullYear();
			const mmddyyyy = `${mm}${dd}${yyyy}`;
			const activeClass = date.toDateString() === actualDate.toDateString() ? "active" : "";
			allDates.push(<div id={mmddyyyy} key={mmddyyyy} className={`date ${activeClass}`} onClick={() => manageSelectDate(mmddyyyy)}>{dd}</div>);
			if (eventFastLookUp !== null && eventFastLookUp.has(mmddyyyy)){
				for(const event of events){
					if (event.date === mmddyyyy){
						document.getElementById(mmddyyyy).innerHTML += <p className="EventInCalendar">{event.startTime} - {event.name}</p>
					}
				}
			}
		}
		for(let i = 1; i <= 7 - lastDay.getDay(); i++){
			const nextDate = new Date(currYear, currMonth + 1, i);
			const mm = String(nextDate.getMonth() + 1).padStart(2, '0');
			const dd = String(nextDate.getDate()).padStart(2, '0');
			const yyyy = nextDate.getFullYear();
			const mmddyyyy = `${mm}${dd}${yyyy}`;

			allDates.push(<div id={mmddyyyy} key={mmddyyyy} className="date inactive">{dd}</div>);
		}

		setDates(allDates);
	}

	return (
		<div id="calendar-container">
			<div id="calendar-header"> 
				<div id="calendar-header-left">
					<button id="prevButton" onClick={clickedPrevButton}>&lt;</button>
					<div id="month-year">{monthYear}</div>
				</div>
				<button id="nextButton" onClick={clickedNextButton}>&gt;</button>
			</div>
			<div id="calendar-days">
				<div className="calendar-day"> Mon </div>
				<div className="calendar-day"> Tue </div>
				<div className="calendar-day"> Wed </div>
				<div className="calendar-day"> Thu </div>
				<div className="calendar-day"> Fri </div>
				<div className="calendar-day"> Sat </div>
				<div className="calendar-day"> Sun </div>
			</div>
			<div id="calendar-dates">
				{dates}
			</div>
		</div>
	)
}

export default Calendar;