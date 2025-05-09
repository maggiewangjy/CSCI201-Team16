import React from "react";
import "../styles/continueAsGuest.css";

const ContinueAsGuest = ({ onContinue }) => {
  return (
    <div className="guest-container">
      <h2 className="guest-title">
        Continue as <br /> Member (Guest)
      </h2>
      <button className="guest-button" onClick={onContinue}>
        Cont. <span className="guest-arrow">➔</span>
      </button>
    </div>
  );
};

export default ContinueAsGuest;
