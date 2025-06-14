import Navbar from "./components/navbar/Navbar"
import { Link } from 'react-router-dom';
import { Routes, Route } from 'react-router-dom';
import Login from './components/login/Login.jsx';
import Register from "./components/register/Register.jsx";
import Sidebar from './components/sidebar/Sidebar.jsx';
import { useLocation } from "react-router-dom";

function App() {
  
  const location = useLocation()
  const hideside = ['/login' , '/register' , "/" ]
  const shouldShowNavbar = !hideside.includes(location.pathname);
  return(
     <>
      <Navbar />
      {shouldShowNavbar && <Sidebar/> }
      
      <div>

      
      </div>
      
      <Routes>
        {/* <Route path="/mypaper" element={<MyPaper />} />
        <Route path="/generate" element={<Generate />} /> */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
      </Routes>
    </>
  )
}

export default App
