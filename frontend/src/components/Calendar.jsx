import { useEffect, useState } from "react";
import "../styles/calendar.css";

const currDate = new Date();
const actualDate = new Date();

function Calendar({selectEventDate}) {
	const [dates, setDates] = useState([]);
	const [monthYear, setMonthYear] = useState("");

	useEffect(() => {
		calendar();
	}, []);

	const clickedPrevButton = () => {
		currDate.setMonth(currDate.getMonth() - 1);
		const activeDate = document.querySelector(".date.active");
		if (activeDate !== null && activeDate.id !== null) {
			document.getElementById(activeDate.id).classList.remove("active");
		}
		calendar();
	}

	const clickedNextButton = () => {
		currDate.setMonth(currDate.getMonth() + 1);
		const activeDate = document.querySelector(".date.active");
		if (activeDate !== null && activeDate.id !== null) {
			document.getElementById(activeDate.id).classList.remove("active");
		}
		// Need to fix this bug
		// if (currDate.getMonth() === actualDate.getMonth()){
		// 	selectEventDate(currDate.getDate());
		// }
		calendar();
	}

	const manageSelectDate = (selectedDate) => {
		selectEventDate(selectedDate);
		const activeDate = document.querySelector(".date.active");
		if (activeDate !== null && activeDate.id !== null) {
			document.getElementById(activeDate.id).classList.remove("active");
		}
		document.getElementById(`date-${selectedDate}`).classList.add("active");
	}

	const calendar = () => {
		const currYear = currDate.getFullYear();
		const currMonth = currDate.getMonth();
		const firstDay = new Date(currYear, currMonth, 1);
		const lastDay = new Date(currYear, currMonth + 1, 0);
		const lastDayPrevMonth = new Date(currYear, currMonth, 1);

		setMonthYear(currDate.toLocaleString("default", {month: "long", year: "numeric"}));

		const allDates =[];
		for(let i = firstDay.getDay(); i > 1; i--){
			const prevDate = new Date(currYear, currMonth, 0 - i + 1);
			// id not really necessary for now
			allDates.push(<div key={`prev-${i}`} className="date inactive">{prevDate.getDate()}</div>);
		}
		for(let i = 1; i <= lastDay.getDate(); i++){
			const date = new Date(currYear, currMonth, i);
			if (date.toDateString() === currDate.toDateString()){
				console.log(date);
				console.log(currDate);
			}
			const activeClass = date.toDateString() === actualDate.toDateString() ? "active" : "";
			allDates.push(<div id={`date-${i}`} key={`curr-${i}`} className={`date ${activeClass}`} onClick={() => manageSelectDate(date.getDate())}>{date.getDate()}</div>);
		}
		for(let i = 1; i <= 7 - lastDay.getDay(); i++){
			const nextDate = new Date(currYear, currMonth + 1, i);
			// id not really necessary for the end and start portions
			allDates.push(<div id={`date-${lastDay.getDate() + i}`} key={`next-${i}`} className="date inactive">{nextDate.getDate()}</div>);
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