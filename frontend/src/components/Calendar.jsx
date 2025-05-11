import { useEffect, useState } from "react";
import "../styles/calendar.css";

const currDate = new Date();
const actualDate = new Date();

function Calendar({ selectEventDate }) {
	const [dates, setDates] = useState([]);
	const [monthYear, setMonthYear] = useState("");
	const [activeDate, setActiveDate] = useState(`${String(currDate.getMonth() + 1).padStart(2, '0')}${String(currDate.getDate()).padStart(2, '0')}${currDate.getFullYear()}`); 

	const load = async () => {
		const mm = String(currDate.getMonth() + 1).padStart(2, '0');
		const yyyy = currDate.getFullYear();
		const mmyyyy = `${mm}${yyyy}`;
		const events = await fetchEvents(mmyyyy);
		
		selectEventDate(activeDate);
		calendar(events);
	}

	useEffect(() => {
		load();
	}, [activeDate]);  

	// useEffect(() => {
	// }, [eventsByDate]);

	const clickedPrevButton = async () => {
		currDate.setMonth(currDate.getMonth() - 1);
		const activeDateDiv = document.querySelector(".date.active");
		const mm = String(currDate.getMonth() + 1).padStart(2, '0');
		const dd = String(currDate.getDate()).padStart(2, '0');
		const yyyy = currDate.getFullYear();
		const currDatemmyyyy = `${mm}${yyyy}`;
		const actualmm = String(actualDate.getMonth() + 1).padStart(2, '0');
		const actualyyyy = actualDate.getFullYear();
		const actualmmyyyy = `${actualmm}${actualyyyy}`;
		if (activeDateDiv !== null && activeDateDiv.id !== null) {
			document.getElementById(activeDateDiv.id).classList.remove("active");
		}

		if (currDatemmyyyy === actualmmyyyy) {
			selectEventDate(`${actualmm}${dd}${actualyyyy}`);
			setActiveDate(`${actualmm}${dd}${actualyyyy}`);
		} else {
			selectEventDate(`${mm}01${yyyy}`);
			setActiveDate(`${mm}01${yyyy}`);
		}
		await load();
	}

	const clickedNextButton = async () => {
		currDate.setMonth(currDate.getMonth() + 1);
		const activeDateDiv = document.querySelector(".date.active");
		const mm = String(currDate.getMonth() + 1).padStart(2, '0');
		const dd = String(currDate.getDate()).padStart(2, '0');
		const yyyy = currDate.getFullYear();
		const currDatemmyyyy = `${mm}${yyyy}`;
		const actualmm = String(actualDate.getMonth() + 1).padStart(2, '0');
		const actualyyyy = actualDate.getFullYear();
		const actualmmyyyy = `${actualmm}${actualyyyy}`;

		if (activeDateDiv !== null && activeDateDiv.id !== null) {
			document.getElementById(activeDateDiv.id).classList.remove("active");
		}

		if (currDatemmyyyy === actualmmyyyy) {
			selectEventDate(`${actualmm}${dd}${actualyyyy}`);
			setActiveDate(`${actualmm}${dd}${actualyyyy}`);
		} else {
			selectEventDate(`${mm}01${yyyy}`);
			setActiveDate(`${mm}01${yyyy}`);
		}
		await load();
	}

	const manageSelectDate = (selectedDate) => {
		selectEventDate(selectedDate);
		console.log(selectedDate);
		const activeDate = document.querySelector(".date.active");
		if (activeDate !== null && activeDate.id !== null) {
			document.getElementById(activeDate.id).classList.remove("active");
		}
		document.getElementById(selectedDate).classList.add("active");
	}

	const fetchEvents = async (mmyyyy) => {
		// console.log(mmyyyy);
		const URL = `http://localhost:8080/Team16_CSCI201_Project/GetEventByMonth?dateMonth=${encodeURIComponent(mmyyyy)}`
		const response = await fetch(URL);
		const result = await response.json();
		if (result.status === "success") {
			// console.log(result.data);
			const map = new Map();
			result.data.forEach(event => {
				if (!map.has(event.date)) {
					map.set(event.date, []);
				}
				const eventDetails = {
					name: event.name,
					startTime: event.startTime,
					endTime: event.endTime,
					location: event.location,
					agenda: event.agenda,
					eventID: event.eventID
				};
				map.get(event.date).push(eventDetails);
			});
			// console.log(map);
			return map;
		} else {
			alert(result.message);
		}
	}

	const calendar = (events) => {
		const currYear = currDate.getFullYear();
		const currMonth = currDate.getMonth();
		const firstDay = new Date(currYear, currMonth, 1);
		const lastDay = new Date(currYear, currMonth + 1, 0);
		setMonthYear(currDate.toLocaleString("default", { month: "long", year: "numeric" }));
	
		const allDates = [];
		for (let i = firstDay.getDay(); i > 1; i--) {
			const prevDate = new Date(currYear, currMonth, 0 - i + 1);
			const mm = String(prevDate.getMonth() + 1).padStart(2, '0');
			const dd = String(prevDate.getDate()).padStart(2, '0');
			const yyyy = prevDate.getFullYear();
			const mmddyyyy = `${mm}${dd}${yyyy}`;

			allDates.push(<div id={mmddyyyy} key={mmddyyyy} className="date inactive">{dd}</div>);
		}

		console.log(activeDate);
		for (let i = 1; i <= lastDay.getDate(); i++) {
			const date = new Date(currYear, currMonth, i);
			const mm = String(date.getMonth() + 1).padStart(2, '0');
			const dd = String(date.getDate()).padStart(2, '0');
			const yyyy = date.getFullYear();
			const mmddyyyy = `${mm}${dd}${yyyy}`;
			console.log("===========");
			console.log("activeDate: " + activeDate);
			console.log("currDate: " + mmddyyyy);
			console.log(activeDate === mmddyyyy);

			const activeClass = activeDate === mmddyyyy ? "active" : "";
			
			allDates.push(
				<div id={mmddyyyy} key={mmddyyyy} className={`date ${activeClass}`} onClick={() => manageSelectDate(mmddyyyy)}>
					{dd}
					{events.get(mmddyyyy)?.map((event) => (
						<div className="EventInCalendar-container">
							<div className="circle"></div>
							<p key={event.eventID} className="EventInCalendar">{event.name}</p>
						</div>
					))}
				</div>
			);
		}
		for (let i = 1; i <= 7 - lastDay.getDay(); i++) {
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
					<button id="prevButton" onClick={clickedPrevButton}>&larr;</button>
					<div id="month-year">{monthYear}</div>
				</div>
				<button id="nextButton" onClick={clickedNextButton}>&rarr;</button>
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