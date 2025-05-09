import { useEffect } from "react";
import "../styles/LoginForm.css";

const LoginForm = ({ onSubmit }) => {
    return (
      <form onSubmit={onSubmit} className="login-form">
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
{/*         <p className="forgot-password">Forgot Password?</p> */}
      </form>
    );
  };
  
  export default LoginForm;
