import { useEffect } from "react";
import { Navigate } from "react-router-dom";

function UserVerification({children}) {
    if (localStorage.getItem("logged-in") === "true"){
        return children;
    } else {
        return <Navigate to="/login"/>;
    }
}

export default UserVerification;