import React, { useState } from "react";
import ContinueAsGuest from "../components/continueAsGuest.jsx";
import logo from "../images/logoBackGroundRemoved.png";
import "../styles/clubLeaderSignUp.css";

const MemberSignUp = () => {
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSignUp = async (e) => {
    e.preventDefault();
    setError("");
    setSuccess("");
    setLoading(true);
    
    // Get form values directly from the form
    const name = e.target.name.value;
    const email = e.target.email.value;
    const password = e.target.password.value;
    
    try {
      // Create URL-encoded form data as expected by the servlet
      const formData = new URLSearchParams();
      formData.append("name", name);
      formData.append("email", email);
      formData.append("password", password);
      
      // Make POST request to the servlet
      const response = await fetch("/Register", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: formData.toString(),
      });
      
      const data = await response.json();
      
      if (response.ok) {
        // Registration successful
        setSuccess(data.message || "Registration successful! You can now login.");
        // Optional: Clear form
        e.target.reset();
        
        // Optional: Redirect to login page after a delay
        setTimeout(() => {
          window.location.href = "/login";
        }, 2000);
      } else {
        // Registration failed
        setError(data.message || "Registration failed. Please try again.");
      }
    } catch (err) {
      console.error("Registration error:", err);
      setError("An error occurred. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  const handleContinueAsGuest = () => {
    // Redirect to guest page
    window.location.href = "/guestPage";
  };

  return (
    <div className="signup-container">
      <div className="signup-left">
        <div className="logo-container">
          <img src={logo} alt="Club Logo" className="logo" />
        </div>
        
        <div className="signup-form-card">
          <h2 className="signup-title">New Club Leader Registration</h2>
          
          {error && <div className="error-message">{error}</div>}
          {success && <div className="success-message">{success}</div>}
          
          <form onSubmit={handleSignUp} className="signup-form">
            <div className="form-group">
              <input
                type="text"
                name="name"
                placeholder="Full Name"
                className="signup-input"
                required
              />
            </div>
            <div className="form-group">
              <input
                type="email"
                name="email"
                placeholder="Email Address"
                className="signup-input"
                required
              />
            </div>
            <div className="form-group">
              <input
                type="password"
                name="password"
                placeholder="Password"
                className="signup-input"
                required
              />
            </div>
            <button 
              type="submit" 
              className="signup-button" 
              disabled={loading}
            >
              {loading ? "Signing Up..." : "Sign Up"}
            </button>
            <p className="login-link">
              Already have an account? <a href="/login">Login here</a>
            </p>
          </form>
        </div>
      </div>
      
      <div className="guest-section">
        <div className="guest-content">
          <ContinueAsGuest onContinue={handleContinueAsGuest} />
        </div>
      </div>
    </div>
  );
};

export default MemberSignUp;
