import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/createEvent.css";

function EditEvent({ eventId, onClose }) {
  const navigate = useNavigate();

  const [errors, setErrors] = useState({});
  const [loading, setLoading] = useState(true);

  const [formData, setFormData] = useState({
    name: '',
    date: '',
    dateMonth: '',
    startTime: '',
    endTime: '',
    location: '',
    agenda: ''
  });

  useEffect(() => {
    async function loadEvent() {
      try {
        const URL = `http://localhost:8080/Team16_CSCI201_Project/GetEventByID?eventID=${encodeURIComponent(eventId)}`;
        const response = await fetch(URL);
        const result = await response.json();

        // Format date to fit date type input field 
        const formatDate = (date) => {
          // Separate month, day, and year 
          const numMonth = date.substring(0, 2); 
          const day = date.substring(2, 4);
          const year = date.substring(4, 8);
          
          // Return formatted date
          return `${year}-${numMonth}-${day}`;
        };

        // Format time to fit time type input field 
        const formatTime = (t) => t ? t.slice(0, 5) : '';

        if(result.status === "success" && result.data.name){
          setFormData({
            name: result.data.name,
            date: formatDate(result.data.date),
            startTime: formatTime(result.data.startTime),
            endTime: formatTime(result.data.endTime),
            location: result.data.location,
            agenda: result.data.agenda
          });
        }

      } catch (err) {
        console.log("Failed to load event:", err);
        alert("Could not load event data.");
      } finally {
        setLoading(false);
      }
    }
    loadEvent();
  }, [eventId]);

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
    const pad = (val) => val.toString().padStart(2, '0');

    const cleanDate = formData.date.replace(/-/g, "");

    const year = cleanDate.substring(0, 4); 
    const month = cleanDate.substring(5, 6) === '-' ? cleanDate.substring(4, 5) : cleanDate.substring(4, 6); 
    const day = cleanDate.substring(6, 8); 
  
    const date = `${month}${day}${year}`;    
    const dateMonth = `${month}${year}`; 

    return {
      ...formData,
      date, 
      dateMonth,
      startTime: `${formData.date} ${formData.startTime}:00`,
      endTime: `${formData.date} ${formData.endTime}:00`
    };
  };

  const handleSave = async (e) => {
    e.preventDefault();
    try {
      const formatted = formatForBackend();
      const URL = `http://localhost:8080/Team16_CSCI201_Project/UpdateEventInfo`;
      const response = await fetch(URL, {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: new URLSearchParams({
          eventID: eventId,
          name: formatted.name,
          date: formatted.date,
          dateMonth: formatted.dateMonth,
          startTime: formatted.startTime,
          endTime: formatted.endTime,
          location: formatted.location,
          agenda: formatted.agenda
        }).toString()
      });

      const result = await response.json();

      if(result.status === "success"){
        navigate("/clubLeaderPage");
      }
    } catch (err) {
      console.error(err);
      alert("Failed to save changes.");
    }
  };

  if (loading) return <p>Loading…</p>;

  return (
    <div id="event">
      <div>
          <h1 className="titles"><strong>Edit Event</strong></h1>
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
                      <button>Save Changes</button>
                  </div>
              </form>
          </div>
      </div>
    </div>
  );
}

export default EditEvent;
