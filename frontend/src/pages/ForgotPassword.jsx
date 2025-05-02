import React from "react";
import ContinueAsGuest from "../components/continueAsGuest.jsx";
import logo from "../images/logoBackGroundRemoved.png";
import "../styles/forgotPassword.css";

const ForgotPassword = () => {
  const handleSubmit = (e) => {
    e.preventDefault();
    //handle
  };

  const handleContinueAsGuest = () => {
    //back to login 
    window.location.href = '/login';
  };

  const handleGoBack = () => {
    window.history.back();
  };

  return (
    <div className="forgot-container">
      <div className="forgot-left">
        <div className="logo-container">
          <img src={logo} alt="Club Logo" className="logo" />
        </div>
        
        <div className="forgot-card">
          <h2 className="forgot-title">Forgot Password?</h2>
          
          <form onSubmit={handleSubmit} className="forgot-form">
            <div className="form-group">
              <label htmlFor="email">Email</label>
              <input
                type="email"
                id="email"
                placeholder="School Email"
                name="email"
                className="forgot-input"
                required
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="security1">*Security Question #1*</label>
              <input
                type="text"
                id="security1"
                placeholder="Answer"
                name="security1"
                className="forgot-input"
                required
              />
            </div>
            
            <div className="form-group">
              <label htmlFor="security2">*Security Question #2*</label>
              <input
                type="text"
                id="security2"
                placeholder="Answer"
                name="security2"
                className="forgot-input"
                required
              />
            </div>
            
            <div className="forgot-buttons">
              <button type="submit" className="forgot-button">
                Login
              </button>
              <span className="go-back" onClick={handleGoBack}>
                Go Back
              </span>
            </div>
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

export default ForgotPassword;