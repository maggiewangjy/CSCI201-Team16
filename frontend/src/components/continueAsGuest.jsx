import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/continueAsGuest.css";

const ContinueAsGuest = () => {
  const navigate = useNavigate();

  const handleContinue = () => {
    navigate("/clubLeaderPage");
  };

  return (
    <div className="guest-container">
      <h2 className="guest-title">
        Continue as <br /> Guest
      </h2>
      <button className="guest-button" onClick={handleContinue}>
        Continue <span className="guest-arrow">➔</span>
      </button>
    </div>
  );
};

export default ContinueAsGuest;
