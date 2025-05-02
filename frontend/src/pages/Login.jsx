import React from "react";
//import NavBar from "../components/navBar.jsx";
import LoginForm from "../components/loginForm.jsx";
import SignUpCard from "../components/signUpCard.jsx";
import ContinueAsGuest from "../components/continueAsGuest.jsx";
import logo from "../images/logoBackGroundRemoved.png";
import "../styles/login.css";

const Login = () => {
  const handleLogin = (e) => {
    e.preventDefault();
    // Handle login logic here
  };

  const handleContinueAsGuest = () => {
    // Handle guest login logic here
  };

  const handleSignUp = () => {
    window.location.href = '/clubLeaderSignUp';
    
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
            <LoginForm onSubmit={handleLogin} />
          </div>
          
          <div className="signup-wrapper">
            <SignUpCard onSignUpClick={handleSignUp} />
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