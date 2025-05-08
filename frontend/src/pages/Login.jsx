import React from "react";
import NavBar from "../components/navBar.jsx";
import LoginForm from "../components/loginForm.jsx";
import SignUpCard from "../components/signUpCard.jsx";
import ContinueAsGuest from "../components/continueAsGuest.jsx";
import logo from "../images/logoBackGroundRemoved.png";
import "../styles/login.css";

const Login = () => {

  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  const handleLogin = async (e) => {
    e.preventDefault();
    setError("");
    setLoading(true);
    
    // Get form values
    const username = e.target.username.value;
    const password = e.target.password.value;
    
    try {
      // Create URL-encoded form data as expected by the servlet
      const formData = new URLSearchParams();
      formData.append("email", username); // Map username field to email parameter expected by servlet
      formData.append("password", password);
      
      // Make POST request to the servlet
      const response = await fetch("/Login", {
        method: "POST",
        headers: {
          "Content-Type": "application/x-www-form-urlencoded",
        },
        body: formData.toString(),
      });
      
      const data = await response.json();
      
      if (response.ok) {
        // Login successful
        console.log("Login successful", data);
        // Store user ID in localStorage or sessionStorage
        localStorage.setItem("userID", data.data.userID);
        
        window.location.href = "/memberPage";
      } else {
        // Login failed
        setError(data.message || "Login failed. Please try again.");
      }
    } catch (err) {
      console.error("Login error:", err);
      setError("An error occurred. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  const handleContinueAsGuest = () => {
    window.location.href = "/guestPage";
  };

  const handleSignUp = () => {
    window.location.href = "/memberSignUp";
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
              <p className="forgot-password">Forgot Password?</p>
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