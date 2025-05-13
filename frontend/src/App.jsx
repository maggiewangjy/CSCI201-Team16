import react from 'react';
import {BrowserRouter, Routes, Route, Navigate} from "react-router-dom";
import "./styles/app.css";
import Login from "./pages/Login.jsx";
import ClubLeaderPage from "./pages/clubLeaderPage.jsx"
import NotFound from "./pages/NotFound.jsx";
import MemberSignUp from "./pages/MemberSignUp.jsx"

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={ <Login/> }/>
        <Route path="/login" element={ <Login/> }/>
        <Route
          path="/clubLeaderPage"
          element={
              <ClubLeaderPage />
          }
        />
        <Route path="*" element={ <NotFound/> }/>
        <Route path="/signup" element= {<MemberSignUp/>}/>
      </Routes>
    </BrowserRouter>
  )
}

export default App