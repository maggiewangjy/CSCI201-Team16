import React from "react";
import ContinueAsGuest from "../components/continueAsGuest.jsx";
import logo from "../images/logoBackGroundRemoved.png";
import "../styles/login.css";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";


const Login = () => {
  const navigate = useNavigate();
  const [invalidFormInput, setInvalidFormInput] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();

    const email = e.target.email.value;
    const password = e.target.password.value;

    // console.log(email);
    // console.log(password);

    try {
<<<<<<< HEAD
      // Create URL-encoded form data as expected by the servlet
      const formData = new URLSearchParams();
      formData.append("email", username); // Map username field to email parameter expected by servlet
      formData.append("password", password);
      
     
      const response = await fetch("http://localhost:8080/Login", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
          "Credentials" : "same-origin"
        },
        body: JSON.stringify({ key: 'value' })
=======
      e.preventDefault;
      const response = await fetch("http://localhost:8080/Team16_CSCI201_Project/Login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ email, password }),
>>>>>>> login
      });
      
      const result = await response.json();

      if (result.status === "success") {
        setInvalidFormInput(false);
        console.log("Login successful", result);
        localStorage.clear();
        localStorage.setItem("userID", result.data.userID);
        localStorage.setItem("logged-in", "true");
        navigate("/clubLeaderPage");
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

  const handleSignUp = () => {
<<<<<<< HEAD
    window.location.href = "/signup";
=======
    localStorage.clear();
    navigate("/signup");
>>>>>>> login
  };

  return (
    <div className="login-container">
      <div className="login-left">
        <div className="logo-container">
          <img src={logo} alt="Club Logo" className="logo" />
        </div>

        <div className="login-content">
          <div className="login-card">
            <h2 className="login-title">Club Leader Login</h2>

            <form onSubmit={handleLogin} className="custom-login-form">
              <input
                type="email"
                placeholder="Email"
                name="email"
                className="login-input"
                required
              />
              <input
                type="password"
                placeholder="Password"
                name="password"
                className="login-input"
                required
              />
              {invalidFormInput && <div className="error-message">Invalid Email or Password. Try Again</div>}
              <button type="submit" className="login-button">
                Login
              </button>
            </form>
          </div>

          <div className="signup-card">
            <h2 className="signup-title">New Club Leader?</h2>
            <button className="signup-button" onClick={handleSignUp}>
              Sign up
            </button>
          </div>
        </div>
      </div>

      <div className="guest-section">
        <ContinueAsGuest onContinue={handleContinueAsGuest} />
      </div>
    </div>
  );
};

export default Login;
