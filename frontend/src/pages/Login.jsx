import React from "react";
import { useState } from "react";
import { useNavigate } from "react-router-dom";
import ContinueAsGuest from "../components/continueAsGuest.jsx";
import logo from "../images/logoBackGroundRemoved.png";
import "../styles/login.css";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    
    const URL = "http://localhost:8080/backend/";
    try {
      const response = await fetch(`${URL}Login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          email: email,
          password: password
        })
      });
      const data = await response.json();

      if (data.status === 'success') {
        localStorage.setItem("logged-in", "true");
        navigate("/clubLeaderPage");
      } else {
        alert(data.message || 'Login failed. Please try again.');
      }
    } catch (error) {
      console.error('Error:', error);
      alert('An error occurred during login. Please try again.');
    }
  };

  const handleContinueAsGuest = () => {
    localStorage.clear();
    localStorage.setItem("logged-in", "false");
    navigate("/guestPage");
  };

  const handleSignUp = () => {
    navigate("/signUp");
  };

  // const handleForgotPassword = () => {
  //   navigate("/forgotPassword");
  // };

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
                onChange={(e) => setEmail(e.target.value)}
                required
              />
              <input
                type="password"
                placeholder="Password"
                name="password"
                className="login-input"
                onChange={(e) => setPassword(e.target.value)}
                required
              />
              <button type="submit" className="login-button">
                Login
              </button>
              {/* <p className="forgot-password" onClick={handleForgotPassword}>Forgot Password?</p> */}
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
        <div className="guest-content">
          <ContinueAsGuest onContinue={handleContinueAsGuest} />
        </div>
      </div>
    </div>
  );
};

export default Login;