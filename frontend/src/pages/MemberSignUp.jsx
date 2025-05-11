import React, { useState } from "react";
import ContinueAsGuest from "../components/continueAsGuest.jsx";
import logo from "../images/logoBackGroundRemoved.png";
import { useNavigate } from "react-router-dom";
import "../styles/clubLeaderSignUp.css";

const MemberSignUp = () => {
  const navigate = useNavigate();
  const [invalidFormInput, setInvalidFormInput] = useState(false);

  const handleRegister = async (e) => {
    e.preventDefault();

    const name = e.target.name.value;
    const email = e.target.email.value;
    const password = e.target.password.value;

    try {
      e.preventDefault;
      const response = await fetch("http://localhost:8080/Team16_CSCI201_Project/Register", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ name, email, password }),
      });

      const result = await response.json();

      if (result.status === "success") {
        setInvalidFormInput(false);
        localStorage.clear();
        navigate("/login");
      } else {
        setInvalidFormInput(true);
        console.log(result.message);
      }
    } catch (err) {
      setError("An error occurred");
    } finally {
    }
  };

  const handleContinueAsGuest = () => {
    localStorage.clear();
    localStorage.setItem("logged-in", "false")
    navigate("/clubLeaderPage");
  };

  return (
    <div className="signup-container">
      <div className="signup-left">
        <div className="logo-container">
          <img src={logo} alt="Club Logo" className="logo" />
        </div>
        <div id="content">
          <div className="signup-form-card">
            <h2 className="signup-title">New Club Member Registration</h2>
            <form onSubmit={handleRegister} className="signup-form">
              <input
                type="text"
                name="name"
                placeholder="Full Name"
                className="signup-input"
                required
              />
              <input
                type="email"
                name="email"
                placeholder="Email Address"
                className="signup-input"
                required
              />
              <input
                type="password"
                name="password"
                placeholder="Password"
                className="signup-input"
                required
              />
              {invalidFormInput && <div className="error-message">Email already in Exist</div>}
              <button
                type="submit"
                className="signup-button"
              >
                Register
              </button>
              <p className="login-link">
                Already have an account? <a href="/login">Login here</a>
              </p>
            </form>
          </div>
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

