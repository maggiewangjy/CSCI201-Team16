import React from "react";
import { useNavigate } from "react-router-dom";
import NavBar from "../components/navBar.jsx";
import LoginForm from "../components/loginForm.jsx";
import SignUpCard from "../components/signUpCard.jsx";
import ContinueAsGuest from "../components/continueAsGuest.jsx";
import logo from "../images/logoBackGroundRemoved.png";
import "../styles/login.css";

const Login = () => {
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const username = formData.get('username');
    const password = formData.get('password');

    try {
      const response = await fetch('/Login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ username, password })
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
    localStorage.setItem("logged-in", "true");
    navigate("/clubLeaderPage");
  };

  const handleSignUp = () => {
    navigate("/signup");
  };

  const handleForgotPassword = () => {
    navigate("/forgot-password");
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
                type="text"
                placeholder="Username"
                name="username"
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
              <button type="submit" className="login-button">
                Login
              </button>
              <p className="forgot-password" onClick={handleForgotPassword}>Forgot Password?</p>
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