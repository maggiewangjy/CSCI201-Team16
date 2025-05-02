import React from "react";
import "../styles/signUpCard.css";

const SignUpCard = ({ onSignUpClick }) => {
  return (
    <div className="signup-card">
      <h2 className="signup-title">New Club Leader?</h2>
      <button className="signup-button" onClick={onSignUpClick}>
        Sign up
      </button>
    </div>
  );
};

export default SignUpCard;