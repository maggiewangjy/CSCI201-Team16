import { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import "../styles/createEvent.css";

function CreateEvent(){

    const [formData, setFormData] = useState({
        name: '',
        date: '',
        startTime: '',
        endTime: '',
        location: '',
        agenda: ''
    });

    const [errors, setErrors] = useState({});

    const getCurrentDate = () => {
        const currentDate = new Date();

        const month = String(currentDate.getMonth() + 1).padStart(2, '0');
        const day = String(currentDate.getDate()).padStart(2, '0');
        const year = currentDate.getFullYear();

        return (`${year}-${month}-${day}`);
    }

    function formSubmit(e) {
        e.preventDefault();
        let hasError = false;
        const errorsList = {};
        console.log(formData.date);
        
        // Check name
        if (formData.name.trim() === "") {
            errorsList.name = "Event name is required.";
            hasError = true;
        } 

        // Check date
        if (formData.date.trim() === "") {
            errorsList.date = "Date is required.";
            hasError = true;   
        } 

        // Check start time
        if (formData.startTime === "") {
            errorsList.startTime = "Start time is required.";
            hasError = true;
        } 

        // Check start time
        if (formData.endTime === "") {
            errorsList.endTime = "End time is required.";
            hasError = true;
        } 

        setErrors(errorsList);
        
        // If there are errors, return 
        if (hasError) {
            return;
        }

        handleSave(e);
    }

    const formatForBackend = () => {
        return {
          ...formData,
          date: formData.date, 
          startTime: `${formData.startTime}:00`,
          endTime: `${formData.endTime}:00`
        };
    };

    const navigate = useNavigate()
    
    const handleSave = async (e) => {
        e.preventDefault();
        try {
            const formatted = formatForBackend();
            const URL = `http://localhost:8080/Team16_CSCI201_Project/AddEvent`;
            const response = await fetch(URL, {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: new URLSearchParams({
            name: formatted.name,
            date: formatted.date,
            start: formatted.startTime,
            end: formatted.endTime,
            location: formatted.location,
            agenda: formatted.agenda
            }).toString()
        });
    
        const result = await response.json();
    
        if(result.status === "success"){
            alert('created');
            navigate("/clubLeaderPage");
        }
        else{
            console.log('error:' + result.message);
        }
        } catch (err) {
        console.error(err);
        alert("Failed to save changes.");
        }
    };

    return(
        <div id="event">
            <div>
                <h1 className="titles"><strong>Create Event</strong></h1>
                <div> 
                    <form id="form" onSubmit={formSubmit}> 
                        <div id="form-card">
                            <div>
                                <label>Event Name:</label><br/>
                                <input type="text" placeholder="Event Name" value={formData.name} onChange={(e) => setFormData({ ...formData, name: e.target.value })}/>
                                {errors.name && <div className="error-message">{errors.name}</div>}
                            </div>
                            <div>
                                <label>Date:</label><br/>
                                <input type="date" placeholder="Date" value={formData.date} min={getCurrentDate()} onChange={(e) => setFormData({ ...formData, date: e.target.value })}/>
                                {errors.date && <div className="error-message">{errors.date}</div>}
                            </div>
                            <div id="times">
                                <div>
                                    <label>Start Time: </label><br/>
                                    <input type="time" value={formData.startTime} onChange={(e) => setFormData({ ...formData, startTime: e.target.value })}/>
                                    {errors.startTime && <div className="error-message">{errors.startTime}</div>}
                                </div>
                                <div>
                                    <label>End Time: </label><br/>
                                    <input type="time" value={formData.endTime} onChange={(e) => setFormData({ ...formData, endTime: e.target.value })}/>
                                    {errors.endTime && <div className="error-message">{errors.endTime}</div>}
                                </div>
                            </div>
                            <div><label>Location:</label><br/><input type="text" placeholder="Location" value={formData.location} onChange={(e) => setFormData({ ...formData, location: e.target.value })}/></div>
                            <div><label>Agenda:</label><br/><textarea placeholder="Agenda" value={formData.agenda} onChange={(e) => setFormData({ ...formData, agenda: e.target.value })}/></div>
                        </div>
                        <div id="buttons">
                            <button>Create Event</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    );
}

export default CreateEvent;