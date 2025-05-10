import { useState, useEffect } from 'react';
import "../styles/createEvent.css";

function CreateEvent(){

    const [formData, setFormData] = useState({
        name: '',
        date: '',
        start: '',
        end: '',
        location: '',
        agenda: ''
    });

    const [errors, setErrors] = useState({});

    function formSubmit(e) {
        e.preventDefault();
        let hasError = false;
        const errorsList = {};
        
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
        if (formData.start === "") {
            errorsList.start = "Start time is required.";
            hasError = true;
        } 

        // Check start time
        if (formData.end === "") {
            errorsList.end = "End time is required.";
            hasError = true;
        } 

        setErrors(errorsList);
        
        // If there are errors, return 
        if (hasError) {
            return;
        }
    }


    return(
        <div id="event">
            <div>
                <h1 class="titles"><strong>Create Event</strong></h1>
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
                                <input type="date" placeholder="Date" value={formData.date} onChange={(e) => setFormData({ ...formData, date: e.target.value })}/>
                                {errors.date && <div className="error-message">{errors.date}</div>}
                            </div>
                            <div id="times">
                                <div>
                                    <label>Start Time: </label><br/>
                                    <input type="time" value={formData.start} onChange={(e) => setFormData({ ...formData, start: e.target.value })}/>
                                    {errors.start && <div className="error-message">{errors.start}</div>}
                                </div>
                                <div>
                                    <label>End Time: </label><br/>
                                    <input type="time" value={formData.end} onChange={(e) => setFormData({ ...formData, end: e.target.value })}/>
                                    {errors.end && <div className="error-message">{errors.end}</div>}
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