import React from "react";
import SignUpForm from "../components/signUpForm.jsx";
import ContinueAsGuest from "../components/continueAsGuest.jsx";
import logo from "../images/logoBackGroundRemoved.png";
import "../styles/clubLeaderSignUp.css";

const ClubLeaderSignUp = () => {
  const handleSignUp = (e) => {
    e.preventDefault();
    
    //console.log("Sign up submitted");
  };

  const handleContinueAsGuest = () => {
    
    //console.log("Continue as guest clicked");
  };

  return (
    <div className="signup-container">
      <div className="signup-left">
        <div className="logo-container">
          <img src={logo} alt="Club Logo" className="logo" />
        </div>
        
        <div className="signup-form-card">
          <SignUpForm onSubmit={handleSignUp} />
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

export default ClubLeaderSignUp;
