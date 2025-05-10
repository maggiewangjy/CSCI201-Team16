import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import "../styles/createEvent.css";

function EditEvent() {
  const { eventId } = useParams();
  const navigate = useNavigate();

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
          `/CSCI201-Team16/GetEventById?eventId=${eventId}`
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
        alert("Could not load event data.");
      } finally {
        setLoading(false);
      }
    }
    loadEvent();
  }, [eventId]);

  const handleSave = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch(
        `/CSCI201-Team16/UpdateEvent?eventId=${eventId}`,
        {
          method: "PUT",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ name, date, startTime, endTime, location, description }),
        }
      );
      if (!res.ok) throw new Error(`Save failed: ${res.status}`);
      navigate("/clubLeaderPage");
    } catch (err) {
      console.error(err);
      alert("Failed to save changes.");
    }
  };

  const handleDelete = async () => {
    if (!window.confirm("Are you sure you want to delete this event?")) return;
    try {
      const res = await fetch(
        `/CSCI201-Team16/DeleteEvent?eventId=${eventId}`,
        { method: "DELETE" }
      );
      if (!res.ok) throw new Error(`Delete failed: ${res.status}`);
      navigate("/clubLeaderPage");
    } catch (err) {
      console.error(err);
      alert("Failed to delete event.");
    }
  };

  if (loading) return <p>Loading…</p>;

  return (
    <div id="event">
      <div id="calendar">
        <p>Calendar</p>
      </div>
      <div>
        <h1 className="titles"><strong>Edit Event</strong></h1>
        <form id="form" onSubmit={handleSave}>
          <div>
            <label>Event Name:</label><br/>
            <input
              type="text"
              placeholder="Event Name"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>

          <div>
            <label>Date:</label><br/>
            <input
              type="date"
              value={date}
              onChange={(e) => setDate(e.target.value)}
              required
            />
          </div>

          <div id="times">
            <div>
              <label>Start Time:</label><br/>
              <input
                type="time"
                value={startTime}
                onChange={(e) => setStartTime(e.target.value)}
                required
              />
            </div>
            <div>
              <label>End Time:</label><br/>
              <input
                type="time"
                value={endTime}
                onChange={(e) => setEndTime(e.target.value)}
                required
              />
            </div>
          </div>

          <div>
            <label>Location:</label><br/>
            <input
              type="text"
              placeholder="Location"
              value={location}
              onChange={(e) => setLocation(e.target.value)}
              required
            />
          </div>

          <div>
            <label>Description:</label><br/>
            <textarea
              placeholder="Description"
              value={description}
              onChange={(e) => setDescription(e.target.value)}
              rows={4}
            />
          </div>

          <div id="buttons">
            <button type="submit">Save Changes</button>
            <button
              type="button"
              onClick={handleDelete}
              style={{ marginLeft: "1rem", background: "crimson", color: "white" }}
            >
              Delete Event
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}

export default EditEvent;
