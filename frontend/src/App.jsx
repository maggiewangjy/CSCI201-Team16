import react from 'react';
import {BrowserRouter, Routes, Route, Navigate} from "react-router-dom";
import "./styles/app.css";
import Login from "./pages/Login.jsx";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={ <Login/> }/>

      </Routes>
    </BrowserRouter>
  )
}

export default App