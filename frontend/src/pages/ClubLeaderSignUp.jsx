import React from "react";
import { useNavigate } from "react-router-dom";
import SignUpForm from "../components/signUpForm.jsx";
import ContinueAsGuest from "../components/continueAsGuest.jsx";
import logo from "../images/logoBackGroundRemoved.png";
import "../styles/clubLeaderSignUp.css";

const ClubLeaderSignUp = () => {
  const navigate = useNavigate();

  const handleSignUp = async (e) => {
    e.preventDefault();
    const formData = new FormData(e.target);
    const signUpData = {
      email: formData.get('email'),
      password: formData.get('password'),
      securityQuestion1: formData.get('securityQuestion1'),
      securityAnswer1: formData.get('securityAnswer1'),
      securityQuestion2: formData.get('securityQuestion2'),
      securityAnswer2: formData.get('securityAnswer2')
    };

    console.log('Sending signup data:', signUpData);

    try {
      const response = await fetch('/RegisterServlet', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(signUpData)
      });

      console.log('Response status:', response.status);
      const data = await response.json();
      console.log('Response data:', data);

      if (data.status === 'success') {
        localStorage.setItem("logged-in", "true");
        navigate("/clubLeaderPage");
      } else {
        alert(data.message || 'Sign up failed. Please try again.');
      }
    } catch (error) {
      console.error('Error during signup:', error);
      alert('An error occurred during sign up. Please try again.');
    }
  };

  const handleContinueAsGuest = () => {
    localStorage.setItem("logged-in", "true");
    navigate("/clubLeaderPage");
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