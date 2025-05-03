import React from "react";
import "../styles/signUpForm.css";

const SignUpForm = ({ onSubmit }) => {
  return (
    <div className="signup-form-container">
      <h2 className="signup-form-title">Club Leader Sign Up</h2>
      
      <form onSubmit={onSubmit} className="signup-form">
        <input
          type="text"
          placeholder="Name"
          name="name"
          className="signup-input"
          required
        />
        <input
          type="email"
          placeholder="School Email"
          name="email"
          className="signup-input"
          required
        />
        <input
          type="password"
          placeholder="Password"
          name="password"
          className="signup-input"
          required
        />
        <div className="security-question-group">
          <input
            type="text"
            placeholder="Security Question #1"
            name="securityQuestion1"
            className="signup-input"
            required
          />
          <input
            type="text"
            placeholder="Answer to Security Question #1"
            name="securityAnswer1"
            className="signup-input"
            required
          />
        </div>
        <div className="security-question-group">
          <input
            type="text"
            placeholder="Security Question #2"
            name="securityQuestion2"
            className="signup-input"
            required
          />
          <input
            type="text"
            placeholder="Answer to Security Question #2"
            name="securityAnswer2"
            className="signup-input"
            required
          />
        </div>
        <div className="signup-buttons">
          <button type="submit" className="signup-submit-button">
            Sign Up
          </button>
          <button type="button" className="go-back-button" onClick={() => window.history.back()}>
            Go Back
          </button>
        </div>
      </form>
    </div>
  );
};

export default SignUpForm;