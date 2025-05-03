import react from 'react';
import {BrowserRouter, Routes, Route, Navigate} from "react-router-dom";
import "./styles/app.css";
import Login from "./pages/Login.jsx";
import ClubLeaderPage from "./pages/ClubLeaderPage.jsx"
import UserVerification from "./components/UserVerification.jsx";
import NotFound from "./pages/NotFound.jsx";
import ForgotPassword from "./pages/ForgotPassword.jsx";
import ClubLeaderSignUp from "./pages/ClubLeaderSignUp.jsx";
import GuestPage from "./pages/guestPage.jsx";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={ <Login/> }/>
        <Route path="/login" element={ <Login/> }/>
        <Route path="/forgotPassword" element={ <ForgotPassword/> }/>
        <Route path="/signUp" element={ <ClubLeaderSignUp/> }/>
        <Route path="/guestPage" element={ <GuestPage/> }/>
        <Route
          path="/clubLeaderPage"
          element={
            // <UserVerification>
              <ClubLeaderPage />
            // </UserVerification>
          }
        />
        <Route path="*" element={ <NotFound/> }/>
      </Routes>
    </BrowserRouter>
  )
}

export default App