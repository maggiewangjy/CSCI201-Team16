// src/components/EditEvent.jsx
import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import "../styles/createEvent.css";

function EditEvent() {
  const { eventId } = useParams();

  const [name, setName] = useState("");
  const [date, setDate] = useState("");
  const [startTime, setStartTime] = useState("");
  const [endTime, setEndTime] = useState("");
  const [location, setLocation] = useState("");
  const [description, setDescription] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    async function loadEvent() {
      try {
        const res = await fetch(
          `/Team16_CSCI201_Project/GetEventById?eventId=${eventId}`
        );
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const evt = await res.json();
        setName(evt.name);
        setDate(evt.date);             
        setStartTime(evt.startTime);  
        setEndTime(evt.endTime);
        setLocation(evt.location);
        setDescription(evt.description);
      } catch (err) {
        console.error("Failed to load event:", err);
      } finally {
        setLoading(false);
      }
    }
    loadEvent();
  }, [eventId]);

  if (loading) return <p>Loading…</p>;

  return (
    <div id="event">
      <div id="calendar">
        <p>Calendar</p>
      </div>
      <div>
        <h1 className="titles">
          <strong>Edit Event</strong>
        </h1>
        <form id="form">
          <div>
            <label>Event Name:</label>
            <br />
            <input
              type="text"
              placeholder="Event Name"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />
          </div>

          <div>
            <label>Date:</label>
            <br />
            <input
              type="date"
              placeholder="Date"
              value={date}
              onChange={(e) => setDate(e.target.value)}
            />
          </div>

          <div id="times">
            <div>
              <label>Start Time:</label>
              <br />
              <input
                type="time"
                value={startTime}
                onChange={(e) => setStartTime(e.target.value)}
              />
            </div>

            <div>
              <label>End Time:</label>
              <br />
              <input
                type="time"
                value={endTime}
                onChange={(e) => setEndTime(e.target.value)}
              />
            </div>
          </div>

          <div>
            <label>Location:</label>
            <br />
            <input
              type="text"
              placeholder="Location"
              value={location}
              onChange={(e) => setLocation(e.target.value)}
            />
          </div>

          <div>
            <label>Description:</label>
            <br />
            <textarea
              placeholder="Description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
            />
          </div>

          <div id="buttons">
            <button type="submit">Save Changes</button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default EditEvent;
