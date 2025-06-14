import Navbar from "./components/navbar/Navbar"
import { Link } from 'react-router-dom';
import { Routes, Route } from 'react-router-dom';
import Login from './components/login/Login.jsx';
import Register from "./components/register/Register.jsx";
import Sidebar from './components/sidebar/Sidebar.jsx';
import { useLocation } from "react-router-dom";
import GeneratePaper from "./components/generatepaper/GeneratePaper.jsx";

function App() {
  
  const location = useLocation()
  // const hideside = ['/login' , '/register' , "/" ]
  const shownav = ['/','/login','/register']
  const shouldShowNavbar = shownav.includes(location.pathname);
  
  return(
     <>
      {/* <Navbar /> */}
      {shouldShowNavbar && <Navbar/> }
      
     
      <Routes>
        {/* <Route path="/mypaper" element={<MyPaper />} />
        <Route path="/generate" element={<Generate />} /> */}
        <Route path="/:username/generatepaper" element={<GeneratePaper />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
      </Routes>
    </>
  )
}

export default App
